package com.isaachahn.billedrelay.service;

import com.isaachahn.billedrelay.models.User;
import com.isaachahn.billedrelay.models.entity.Relay;
import com.isaachahn.billedrelay.payload.request.ForceOnRelayRequest;
import com.isaachahn.billedrelay.payload.request.RelayUpdateRequest;
import com.isaachahn.billedrelay.payload.request.SwitchOnUntilRequest;
import com.isaachahn.billedrelay.payload.response.RelayResponse;
import com.isaachahn.billedrelay.repository.RelayRepository;
import com.isaachahn.billedrelay.repository.UserRepository;
import com.isaachahn.billedrelay.security.services.UserDetailsImpl;
import com.isaachahn.billedrelay.util.Util;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelayService {
    private final RelayRepository relayRepository;
    private final UserRepository userRepository;

    public List<Relay> getAllRelays() {
        return relayRepository.findAll();
    }

    public List<RelayResponse> getRelaysByRentalEntity() throws BadRequestException {
        User user = getUser();
        List<Relay> relays = relayRepository.findByRentalEntityOrderByRelayName(user.getRentalEntity());
        return relays.stream().map(Util::mapToRelayResponse).collect(Collectors.toList());
    }

    private User getUser() throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userRepository.findById(userDetails.getId()).orElseThrow(() -> new BadRequestException("User not found"));
    }

    public RelayResponse updateRelay(Long id, RelayUpdateRequest request) throws ChangeSetPersister.NotFoundException {
        Relay relay = relayRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        relay.setRelayDescription(request.getRelayDescription());
        relay.setRelayName(request.getRelayName());
        relay.setRelayWhitelist(request.getRelayWhitelist());

        Relay saved = relayRepository.save(relay);
        return Util.mapToRelayResponse(saved);
    }

    public RelayResponse switchOnUntil(SwitchOnUntilRequest request) throws ChangeSetPersister.NotFoundException, BadRequestException {
        User user = getUser();

        Relay fromRelay = relayRepository.findById(request.getFromRelayId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        Relay toRelay = relayRepository.findById(request.getToRelayId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        if (!fromRelay.getRentalEntity().getId().equals(user.getRentalEntity().getId()) ||
                !toRelay.getRentalEntity().getId().equals(user.getRentalEntity().getId())) {
            throw new BadRequestException("Relay entity does not belong to user");
        }

        // Swap onUntil values
        Long temp = fromRelay.getOnUntil();
        fromRelay.setOnUntil(toRelay.getOnUntil());
        toRelay.setOnUntil(temp);

        relayRepository.save(fromRelay);
        Relay updatedToRelay = relayRepository.save(toRelay);

        return Util.mapToRelayResponse(updatedToRelay);
    }


    public RelayResponse forceOnRelay(ForceOnRelayRequest request) throws ChangeSetPersister.NotFoundException, BadRequestException {
        User user = getUser();
        Long relayId = request.getRelayId();
        Relay relay = relayRepository.findById(relayId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if (relay.getRentalEntity().getId().equals(user.getRentalEntity().getId())) {
            relay.setForcedOn(!relay.isForcedOn());//toggle function, inverted if true
            return Util.mapToRelayResponse(relayRepository.save(relay));
        } else {
            throw new BadRequestException("Relay entity does not belong to user");
        }
    }

    public RelayResponse forceOffRelay(ForceOnRelayRequest request) throws ChangeSetPersister.NotFoundException, BadRequestException {
        User user = getUser();
        Long relayId = request.getRelayId();
        Relay relay = relayRepository.findById(relayId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if (relay.getRentalEntity().getId().equals(user.getRentalEntity().getId())) {
            relay.setOnUntil(System.currentTimeMillis());//toggle function, inverted if true
            return Util.mapToRelayResponse(relayRepository.save(relay));
        } else {
            throw new BadRequestException("Relay entity does not belong to user");
        }
    }

}
