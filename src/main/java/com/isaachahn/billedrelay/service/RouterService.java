package com.isaachahn.billedrelay.service;

import com.isaachahn.billedrelay.models.User;
import com.isaachahn.billedrelay.models.entity.Relay;
import com.isaachahn.billedrelay.models.entity.RentalEntity;
import com.isaachahn.billedrelay.models.entity.Router;
import com.isaachahn.billedrelay.payload.request.RelayPayload;
import com.isaachahn.billedrelay.payload.request.RouterPayload;
import com.isaachahn.billedrelay.payload.response.RelayResponse;
import com.isaachahn.billedrelay.payload.response.RouterResponse;
import com.isaachahn.billedrelay.repository.RelayRepository;
import com.isaachahn.billedrelay.repository.RentalEntityRepository;
import com.isaachahn.billedrelay.repository.RouterRepository;
import com.isaachahn.billedrelay.repository.UserRepository;
import com.isaachahn.billedrelay.security.services.UserDetailsImpl;
import com.isaachahn.billedrelay.util.Util;
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
    private final RouterRepository routerRepository;

    public Router createRouter(RouterPayload payload) {
        Router router = new Router().setRouterHardId(payload.getRouterHardId());
        Router saved = repository.save(router);

        List<RelayPayload> relayPayloads = payload.getRelayPayloads();
        List<Relay> relays = createRelays(saved, relayPayloads);
        return repository.save(saved);
    }

    public List<Relay> createRelays(Router router, List<RelayPayload> relayPayloads) {
        Set<Relay> collect = relayPayloads.stream().map(this::mapToRelay)
                .collect(Collectors.toSet());
        collect.forEach(relay -> relay.setRouter(router));
        List<Relay> relays = relayRepository.saveAll(collect);
        return relays;
    }

    public List<RouterResponse> getAllRouters() {
        List<Router> all = repository.findAll();
        return all.stream().map(router -> {
            RouterResponse routerResponse = new RouterResponse();
            List<RelayResponse> relays = getRelays(router);
            return routerResponse
                    .setRouterHardId(router.getRouterHardId())
                    .setId(router.getId())
                    .setRelays(relays)
                    .setRentalEntity(routerResponse.getRentalEntity());
        }).toList();
    }

    public List<RelayResponse> getRouterRelays(String routerHardId) throws BadRequestException {
        Router byRouterHardId = routerRepository.findByRouterHardId(routerHardId);
        if (byRouterHardId == null) {
            throw new BadRequestException("Router not found");
        } else {
            return getRelays(byRouterHardId);
        }
    }

    public List<RelayResponse> getRelays(Router router) {
        List<Relay> relays = relayRepository.findByRouter(router);
        return relays.stream().map(Util::mapToRelayResponse).toList();
    }

    public List<RouterResponse> getRouters() throws BadRequestException {
        User user = getUser();
        RentalEntity rentalEntity = user.getRentalEntity();

        List<Router> byRentalEntity = repository.findByRentalEntity(rentalEntity);
        return byRentalEntity.stream().map(router -> {
            RouterResponse routerResponse = new RouterResponse();
            List<RelayResponse> relays = getRelays(router);
            return routerResponse
                    .setRouterHardId(router.getRouterHardId())
                    .setId(router.getId())
                    .setRelays(relays)
                    .setRentalEntity(routerResponse.getRentalEntity());
        }).toList();
    }

    public RouterResponse claimRouter(String routerHardId) throws BadRequestException {
        User user = getUser();

        Router byRouterHardId = repository.findByRouterHardId(routerHardId);
        byRouterHardId.setRentalEntity(user.getRentalEntity());
        Router routerResult = repository.save(byRouterHardId);
        List<Relay> relays = relayRepository.findByRouter(byRouterHardId);
        relays.forEach(relay -> relay.setRentalEntity(user.getRentalEntity()));
        List<Relay> relayResult = relayRepository.saveAll(relays);


        RouterResponse routerResponse = new RouterResponse();
        List<RelayResponse> collect = relayResult.stream().map(Util::mapToRelayResponse).toList();
        routerResponse
                .setRouterHardId(routerResult.getRouterHardId())
                .setId(routerResult.getId())
                .setRelays(collect)
                .setRentalEntity(routerResult.getRentalEntity());
        return routerResponse;
    }

//    private RelayResponse mapToRelayResponse(Relay relay) {
//        return new RelayResponse().setRelayDescription(relay.getRelayDescription())
//                .setId(relay.getId())
//                .setRelayWhitelist(relay.getRelayWhitelist())
//                .setRelayName(relay.getRelayName())
//                .setRentalEntity(relay.getRentalEntity())
//                .setPinNumber(relay.getPinNumber())
//                .setRelayHardId(relay.getRelayHardId());
//    }

    private Relay mapToRelay(RelayPayload payload) {
        Relay relay = new Relay()
                .setOnUntil(System.currentTimeMillis())
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
