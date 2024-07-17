package com.isaachahn.billedrelay.service;

import com.isaachahn.billedrelay.models.User;
import com.isaachahn.billedrelay.models.entity.Relay;
import com.isaachahn.billedrelay.payload.request.ForceOnRelayRequest;
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
        List<Relay> relays = relayRepository.findByRentalEntity(user.getRentalEntity());
        return relays.stream().map(Util::mapToRelayResponse).toList();
    }

    private User getUser() throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userRepository.findById(userDetails.getId()).orElseThrow(() -> new BadRequestException("User not found"));
    }

    public RelayResponse forceOnRelay(ForceOnRelayRequest request) throws ChangeSetPersister.NotFoundException, BadRequestException {
        User user = getUser();
        Long relayId = request.getRelayId();
        Relay relay = relayRepository.findById(relayId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if (relay.getRentalEntity().getId().equals(user.getRentalEntity().getId())) {
            relay.setForcedOn(true);
            return Util.mapToRelayResponse(relayRepository.save(relay));
        } else {
            throw new BadRequestException("Relay entity does not belong to user");
        }
    }
}
