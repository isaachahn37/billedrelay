package com.isaachahn.billedrelay.service;

import com.isaachahn.billedrelay.models.User;
import com.isaachahn.billedrelay.models.entity.PackageApplied;
import com.isaachahn.billedrelay.models.entity.Relay;
import com.isaachahn.billedrelay.models.entity.RentalPackage;
import com.isaachahn.billedrelay.payload.request.PackageToRelayAssignmentRequest;
import com.isaachahn.billedrelay.repository.PackageAppliedRepository;
import com.isaachahn.billedrelay.repository.RelayRepository;
import com.isaachahn.billedrelay.repository.RentalPackageRepository;
import com.isaachahn.billedrelay.repository.UserRepository;
import com.isaachahn.billedrelay.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RelayPackageApplicationService {
    private final RelayRepository relayRepository;
    private final RentalPackageRepository rentalPackageRepository;
    private final UserRepository userRepository;
    private final PackageAppliedRepository repository;

    public PackageApplied assignPackageToRelay(PackageToRelayAssignmentRequest request) throws BadRequestException {
        User user = getUser();
        Long rentalId = user.getRentalEntity().getId();
        Relay relay = relayRepository.findById(request.getRelayId()).orElseThrow(() -> new BadRequestException("Relay not found"));
        RentalPackage rentalPackage = rentalPackageRepository.findById(request.getRentalPackageId()).orElseThrow(() -> new BadRequestException("Rental Package not found"));

        if (rentalId.equals(relay.getRentalEntity().getId()) && rentalId.equals(rentalPackage.getRentalEntity().getId())) {
            //set Relay On Until timestamp
            Long onUntil = relay.getOnUntil();
            if (onUntil > System.currentTimeMillis()) {
                onUntil = onUntil + rentalPackage.getMilisAddedOnTime();
            } else {
                onUntil = System.currentTimeMillis() + rentalPackage.getMilisAddedOnTime();
            }
            relay.setOnUntil(onUntil);
            var updatedRelay = relayRepository.save(relay);

            PackageApplied packageApplied = new PackageApplied()
                    .setAppliedTimeStamp(System.currentTimeMillis())
                    .setCreatedByUser(user)
                    .setRentalPackage(rentalPackage)
                    .setRelay(updatedRelay)
                    .setRentalEntity(user.getRentalEntity());

            return repository.save(packageApplied);
        } else {
            throw new BadRequestException("Rental Id mismatch");
        }
    }

    private User getUser() throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userRepository.findById(userDetails.getId()).orElseThrow(() -> new BadRequestException("User not found"));
    }
}
