package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceCreatorServiceTest {

    @InjectMocks
    private DrivingLicenceCreatorService service;

    @Mock
    private InMemoryDatabase database;

    @Mock
    private DrivingLicenceIdGenerationService generationService;

    @Test
    void shouldCreateAndRegisterDrivingLicence() {
        final var socialSecurityNumber = "123456789087654";
        final var generatedUUID = UUID.randomUUID();

        DrivingLicence drivingLicence = DrivingLicence.builder()
                .driverSocialSecurityNumber(socialSecurityNumber)
                .id(generatedUUID)
                .build();

        when(database.save(generatedUUID, any())).thenReturn(drivingLicence);
        when(generationService.generateNewDrivingLicenceId()).thenReturn(generatedUUID);

        final var actual = service.createAndRegisterDrivingLicenceWithSSN(socialSecurityNumber);

        assertThat(actual).isEqualTo(drivingLicence);

        verify(database).save(generatedUUID,drivingLicence);
        verifyNoMoreInteractions(database);

        verify(generationService).generateNewDrivingLicenceId();
        verifyNoMoreInteractions(generationService);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"12345678908765","12345678908765787","ABC","1234567890876AE"})
    void shouldNotCreateDrivingLicence(String invalidSocialSecurityNumber) {

        Exception exception = assertThrows(InvalidDriverSocialSecurityNumberException.class, () -> {
            service.createAndRegisterDrivingLicenceWithSSN(invalidSocialSecurityNumber);
        });

        String expectedMessage = "Invalid Social Security Number";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verifyNoInteractions(database);
        verifyNoInteractions(generationService);
    }


}
