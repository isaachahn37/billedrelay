package com.isaachahn.billedrelay.service;

import com.isaachahn.billedrelay.models.User;
import com.isaachahn.billedrelay.models.entity.Relay;
import com.isaachahn.billedrelay.models.entity.RentalEntity;
import com.isaachahn.billedrelay.models.entity.Router;
import com.isaachahn.billedrelay.payload.request.RelayPayload;
import com.isaachahn.billedrelay.payload.request.RouterPayload;
import com.isaachahn.billedrelay.repository.RelayRepository;
import com.isaachahn.billedrelay.repository.RentalEntityRepository;
import com.isaachahn.billedrelay.repository.RouterRepository;
import com.isaachahn.billedrelay.repository.UserRepository;
import com.isaachahn.billedrelay.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouterService {
    private final RouterRepository repository;
    private final RelayRepository relayRepository;
    private final UserRepository userRepository;

    public Router createRouter(RouterPayload payload) {
        Router router = new Router().setRouterHardId(payload.getRouterHardId());
        Router saved = repository.save(router);

        List<RelayPayload> relayPayloads = payload.getRelayPayloads();
        List<Relay> relays = createRelays(relayPayloads);
        saved.setRelays(new HashSet<>(relays));
        return repository.save(saved);
    }

    public List<Relay> createRelays(List<RelayPayload> relayPayloads) {
        Set<Relay> collect = relayPayloads.stream().map(this::mapToRelay).collect(Collectors.toSet());
        List<Relay> relays = relayRepository.saveAll(collect);
        return relays;
    }

    private Relay mapToRelay(RelayPayload payload) {
        Relay relay = new Relay()
                .setPinNumber(payload.getPinNumber())
                .setRelayHardId(payload.getRelayHardId())
                .setRelayName(payload.getRelayName())
                .setRelayDescription(payload.getRelayDescription())
                .setRelayWhitelist(payload.getRelayWhitelist());
        return relay;
    }



    private User getUser() throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userRepository.findById(userDetails.getId()).orElseThrow(() -> new BadRequestException("User not found"));
    }
}
