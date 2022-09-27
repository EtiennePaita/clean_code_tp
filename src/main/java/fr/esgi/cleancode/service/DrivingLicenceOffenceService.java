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


    }

    private DrivingLicence findDrivingLicenceByID(UUID licenceId) throws ResourceNotFoundException {


    }

    public DrivingLicence removePoints(DrivingLicence drivingLicence, int pointToRemove) {


    }

}
