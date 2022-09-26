package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DrivingLicenceFinderServiceTest {

    @InjectMocks
    private DrivingLicenceFinderService service;

    @Mock
    private InMemoryDatabase database;

    @Test
    void should_find() {
        final var drivingLicenceID = UUID.randomUUID();
        DrivingLicence expectedDrivingLicence = DrivingLicence.builder().id(drivingLicenceID).build();

        when(database.findById(drivingLicenceID)).thenReturn(Optional.of(expectedDrivingLicence));

        final var actual = service.findById(drivingLicenceID);

        assertThat(actual).containsSame(expectedDrivingLicence);
        verify(database).findById(drivingLicenceID);
        verifyNoMoreInteractions(database);
    }

    @Test
    void should_not_find() {
        final var drivingLicenceID = UUID.randomUUID();
        //DrivingLicence noneExpectedDrivingLicence = DrivingLicence.builder().build();
        //DrivingLicence expectedDrivingLicence = DrivingLicence.builder().build();

        when(database.findById(any())).thenReturn(Optional.empty());

        final var actual = service.findById(drivingLicenceID);

        assertThat(actual).isEmpty();
        verify(database).findById(drivingLicenceID);
        verifyNoMoreInteractions(database);    }
}