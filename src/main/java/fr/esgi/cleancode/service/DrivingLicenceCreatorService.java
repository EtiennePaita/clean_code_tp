package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class DrivingLicenceCreatorService {

    private final InMemoryDatabase database;
    private final DrivingLicenceIdGenerationService generationService;

    public DrivingLicence createAndRegisterDrivingLicenceWithSSN(String socialSecurityNumber) throws InvalidDriverSocialSecurityNumberException {

    }

    private boolean checkSSNValidity(String socialSecurityNumber) throws InvalidDriverSocialSecurityNumberException {

    }

    private DrivingLicence createDrivingLicence(String socialSecurityNumber) {

    }

    private boolean saveDrivingLicence(DrivingLicence newDrivingLicence) {

    }
}
