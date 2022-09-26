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
        checkSSNValidity(socialSecurityNumber);
        return saveDrivingLicence(createDrivingLicence(socialSecurityNumber));
    }

    private void checkSSNValidity(String socialSecurityNumber) throws InvalidDriverSocialSecurityNumberException {
        if((socialSecurityNumber == null) || (socialSecurityNumber.length() != 15) || !socialSecurityNumber.matches("^[0-9]+$")) {
            throw new InvalidDriverSocialSecurityNumberException("Invalid Social Security Number");
        }
    }

    private DrivingLicence createDrivingLicence(String socialSecurityNumber) {
        return DrivingLicence.builder()
                .id(generationService.generateNewDrivingLicenceId())
                .driverSocialSecurityNumber(socialSecurityNumber)
                .build();
    }

    private DrivingLicence saveDrivingLicence(DrivingLicence newDrivingLicence) {
        return database.save(newDrivingLicence.getId(), newDrivingLicence);
    }
}
