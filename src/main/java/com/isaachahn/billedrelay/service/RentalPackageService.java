package com.isaachahn.billedrelay.service;

import com.isaachahn.billedrelay.models.User;
import com.isaachahn.billedrelay.models.entity.RentalEntity;
import com.isaachahn.billedrelay.models.entity.RentalPackage;
import com.isaachahn.billedrelay.payload.request.RentalPackageCreationRequest;
import com.isaachahn.billedrelay.payload.response.RentalPackagePayload;
import com.isaachahn.billedrelay.repository.RentalPackageRepository;
import com.isaachahn.billedrelay.repository.UserRepository;
import com.isaachahn.billedrelay.security.services.UserDetailsImpl;
import com.isaachahn.billedrelay.util.Util;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalPackageService {
    private final RentalPackageRepository repository;
    private final UserRepository userRepository;

    public RentalPackagePayload createRentalPackage(RentalPackagePayload request) throws BadRequestException {
        User user = getUser();
        RentalEntity rentalEntity = user.getRentalEntity();
        if (rentalEntity == null) {
            throw new BadRequestException("Rental Package creation failed");
        } else {
            RentalPackage mappedToRentalPackage = Util.mapToRentalPackage(request);
            RentalPackage rentalPackage = mappedToRentalPackage
                    .setRentalEntity(rentalEntity)
                    .setLastModified(System.currentTimeMillis());
            RentalPackage saved = repository.save(rentalPackage);
            return Util.mapToRentalPackagePayload(saved);
        }
    }

    public List<RentalPackagePayload> getAllRentalPackages() throws BadRequestException {
        RentalEntity rentalEntity = getUser().getRentalEntity();
        if (rentalEntity == null) {
            throw new BadRequestException("Rental Package creation failed");
        } else {
            return repository.findByRentalEntity(rentalEntity).stream().map(Util::mapToRentalPackagePayload).toList();
        }
    }

    public RentalPackagePayload updateRentalPackage(RentalPackageCreationRequest request, Long packageId) throws BadRequestException {
        RentalPackage savedRentalPackage = repository.findById(packageId)
                .orElseThrow(BadRequestException::new);
        if (savedRentalPackage.getRentalEntity().getId().equals(getUser().getRentalEntity().getId())) {
            savedRentalPackage.setPackageName(request.getPackageName());
            savedRentalPackage.setMilisAddedOnTime(request.getAddedMinutes() * 60 * 1000);
            savedRentalPackage.setPrice(request.getPrice());
            savedRentalPackage.setLastModified(System.currentTimeMillis());

            RentalPackage updated = repository.save(savedRentalPackage);
            return Util.mapToRentalPackagePayload(updated);
        } else {
            throw new BadRequestException("Rental Package update failed");
        }
    }

    public RentalPackage deleteRentalPackage(RentalPackage rentalPackage) throws BadRequestException {
        User user = getUser();

        RentalPackage savedRentalPackage = repository.findById(rentalPackage.getId()).orElseThrow(BadRequestException::new);
        if (savedRentalPackage.getRentalEntity().getId().equals(user.getId())) {
            repository.delete(savedRentalPackage);
        } else {
            throw new BadRequestException("Rental Package delete failed");
        }
        return savedRentalPackage;
    }

    private User getUser() throws BadRequestException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userRepository.findById(userDetails.getId()).orElseThrow(() -> new BadRequestException("User not found"));
    }


}
