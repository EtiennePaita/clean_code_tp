package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DrivingLicenceOffenceService {

    private final InMemoryDatabase database;

    public DrivingLicence updateDrivingLicencePoints(DrivingLicence drivingLicence, int pointToRemove) throws ResourceNotFoundException {
        DrivingLicence licence = findDrivingLicenceByID(drivingLicence.getId());

        licence = removePoints(licence, pointToRemove);
        return saveLicence(licence);
    }

    private DrivingLicence findDrivingLicenceByID(UUID licenceId) throws ResourceNotFoundException {
        final var drivingLicence = database.findById(licenceId);
        if(drivingLicence.isEmpty()) {
            throw new ResourceNotFoundException("Driving licence ID not found");
        }
        return drivingLicence.get();
    }

    private DrivingLicence removePoints(DrivingLicence drivingLicence, int pointToRemove) {
        if (pointToRemove < 0) return drivingLicence;
        int newAvailablePoints = drivingLicence.getAvailablePoints() - pointToRemove;
        if (newAvailablePoints < 0 ) newAvailablePoints = 0;

        return DrivingLicence.builder()
                .id(drivingLicence.getId())
                .driverSocialSecurityNumber(drivingLicence.getDriverSocialSecurityNumber())
                .availablePoints( newAvailablePoints)
                .build();
    }

    private DrivingLicence saveLicence(DrivingLicence drivingLicence) {
        return database.save(drivingLicence.getId(), drivingLicence);
    }

}
