package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceOffenceServiceTest {

    @InjectMocks
    private DrivingLicenceOffenceService service;

    @Mock
    private InMemoryDatabase database;

    @Captor
    private ArgumentCaptor<DrivingLicence> entityCaptor;

    @ParameterizedTest
    @ValueSource(ints = {0,12,5})
    void shouldUpdateDrivingLicencePoints(int pointToRemove) {
        final var generatedUUID = UUID.randomUUID();

        DrivingLicence drivingLicence = DrivingLicence.builder()
                .id(generatedUUID)
                .build();

        int futureAvailablePoints = drivingLicence.getAvailablePoints() - pointToRemove;

        when(database.save(eq(generatedUUID), any(DrivingLicence.class))).thenAnswer(args -> args.getArguments()[1]);
        when(database.findById(generatedUUID)).thenReturn(Optional.of(drivingLicence));

        final var actual = service.updateDrivingLicencePoints(drivingLicence,pointToRemove);

        assertThat(actual.getAvailablePoints()).isEqualTo(futureAvailablePoints);

        verify(database).save(eq(generatedUUID),entityCaptor.capture());
        verify(database).findById(generatedUUID);
        verifyNoMoreInteractions(database);

        assertThat(entityCaptor.getValue().getAvailablePoints()).isEqualTo(futureAvailablePoints);
    }

    @Test
    void shouldNotCreateDrivingLicence() {
        DrivingLicence drivingLicence = DrivingLicence.builder()
                .id(null)
                .build();

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.updateDrivingLicencePoints(drivingLicence, 3);
        });

        String expectedMessage = "Driving licence ID not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(database).findById(null);

    }
}
