package com.isaachahn.billedrelay.service;

import com.isaachahn.billedrelay.models.User;
import com.isaachahn.billedrelay.models.entity.RentalEntity;
import com.isaachahn.billedrelay.models.entity.RentalPackage;
import com.isaachahn.billedrelay.payload.request.RentalPackageCreationRequest;
import com.isaachahn.billedrelay.repository.RentalPackageRepository;
import com.isaachahn.billedrelay.repository.UserRepository;
import com.isaachahn.billedrelay.security.services.UserDetailsImpl;
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

    public RentalPackage createRentalPackage(RentalPackageCreationRequest request) throws BadRequestException {
        User user = getUser();
        RentalEntity rentalEntity = user.getRentalEntity();
        if (rentalEntity == null) {
            throw new BadRequestException("Rental Package creation failed");
        } else {
            RentalPackage rentalPackage = new RentalPackage()
                    .setPackageName(request.getPackageName())
                    .setMilisAddedOnTime(request.getAddedMinutes() * 60 * 1000)
                    .setPrice(request.getPrice())
                    .setRelayWhitelist(request.getRelayWhitelist())
                    .setRentalEntity(rentalEntity)
                    .setLastModified(System.currentTimeMillis());
            RentalPackage saved = repository.save(rentalPackage);
            return saved;
        }
    }

    public List<RentalPackage> getAllRentalPackages() throws BadRequestException {
        RentalEntity rentalEntity = getUser().getRentalEntity();
        if (rentalEntity == null) {
            throw new BadRequestException("Rental Package creation failed");
        } else {
            return repository.findByRentalEntity(rentalEntity);
        }
    }

    public RentalPackage updateRentalPackage(RentalPackage rentalPackage) throws BadRequestException {
        RentalPackage savedRentalPackage = repository.findById(rentalPackage.getId())
                .orElseThrow(BadRequestException::new);
        if (savedRentalPackage.getRentalEntity().getId().equals(getUser().getRentalEntity().getId())) {
            savedRentalPackage.setPackageName(rentalPackage.getPackageName());
            savedRentalPackage.setMilisAddedOnTime(rentalPackage.getMilisAddedOnTime());
            savedRentalPackage.setPrice(rentalPackage.getPrice());
            savedRentalPackage.setLastModified(System.currentTimeMillis());

            RentalPackage updated = repository.save(savedRentalPackage);
            return updated;
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
