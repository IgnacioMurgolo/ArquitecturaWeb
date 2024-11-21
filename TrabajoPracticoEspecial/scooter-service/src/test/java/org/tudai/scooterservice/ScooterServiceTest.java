package org.tudai.scooterservice;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.tudai.scooterservice.dto.ScooterDTO;
import org.tudai.scooterservice.dto.StationDTO;
import org.tudai.scooterservice.entity.Scooter;
import org.tudai.scooterservice.feign.MaintenanceClient;
import org.tudai.scooterservice.feign.StationClient;
import org.tudai.scooterservice.repository.ScooterRepository;
import org.tudai.scooterservice.service.ScooterService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@ActiveProfiles("test")
public class ScooterServiceTest {

    @Mock
    private ScooterRepository scooterRepository;

    @Mock
    private StationClient stationClient;

    @Mock
    private MaintenanceClient maintenanceClient;

    @InjectMocks
    private ScooterService scooterService;

    private ScooterDTO scooterDTO;

    @BeforeEach
    void setUp() {
        scooterDTO = new ScooterDTO();
        scooterDTO.setStatus(true);
        scooterDTO.setUbication("Downtown");
        scooterDTO.setKilometersTraveled(150.0);
        scooterDTO.setHoursUsed(100.0);
    }

    @Test
    void testSaveScooter() {

        Scooter mockScooter = new Scooter();
        mockScooter.setStatus(true);
        mockScooter.setUbication("Downtown");
        mockScooter.setKilometersTraveled(150.0);
        mockScooter.setHoursUsed(100.0);
        mockScooter.setId(1L);  // Simular que el ID es generado automáticamente

        // Simulamos el comportamiento del repositorio
        when(scooterRepository.save(any(Scooter.class))).thenReturn(mockScooter);

        // Llamamos al método de servicio
        ScooterDTO savedScooter = scooterService.save(scooterDTO);

        // Verificaciones
        assertNotNull(savedScooter);
        assertEquals(scooterDTO.getUbication(), savedScooter.getUbication());
        assertEquals(scooterDTO.getKilometersTraveled(), savedScooter.getKilometersTraveled());
        assertEquals(scooterDTO.getHoursUsed(), savedScooter.getHoursUsed());
        assertTrue(savedScooter.isStatus());

        // Verificamos que se haya llamado al repositorio
        verify(scooterRepository, times(1)).save(any(Scooter.class));
    }

    @Test
    void testParkScooterAtStation() {
        Long scooterId = 1L;
        Long stationId = 2L;

        // Crear una entidad Scooter simulada
        Scooter mockScooter = new Scooter();
        mockScooter.setId(scooterId);
        mockScooter.setStatus(true);
        mockScooter.setUbication("Downtown");

        // Simular que se obtiene un scooter desde el repositorio
        when(scooterRepository.findById(scooterId)).thenReturn(java.util.Optional.of(mockScooter));

        // Simular llamada al cliente de estación
        doNothing().when(stationClient).addScooterToStation(stationId, scooterId);

        // Llamamos al método de servicio
        ScooterDTO parkedScooter = scooterService.parkScooterAtStation(scooterId, stationId);

        // Verificaciones
        assertNotNull(parkedScooter);
        assertEquals(stationId, parkedScooter.getCurrentStationId());
        assertTrue(parkedScooter.isStatus());

        // Verificamos que el repositorio y el cliente de estación se hayan llamado
        verify(scooterRepository, times(1)).save(any(Scooter.class));
        verify(stationClient, times(1)).addScooterToStation(stationId, scooterId);
    }

    @Test
    void testGetScooterWithDetails() {
        Long scooterId = 1L;

        Scooter mockScooter = new Scooter();
        mockScooter.setId(scooterId);
        mockScooter.setStatus(true);
        mockScooter.setUbication("Downtown");

        when(scooterRepository.findById(scooterId)).thenReturn(java.util.Optional.of(mockScooter));


        StationDTO mockStation = new StationDTO();
        mockStation.setName("Main Station");
        mockStation.setUbication("Downtown");

        when(stationClient.getStationById(anyLong())).thenReturn(mockStation);

        List<Long> mockMaintenanceIds = List.of(1L, 2L);
        when(maintenanceClient.getMaintenanceIdsByScooterId(anyLong())).thenReturn(mockMaintenanceIds);


        ScooterDTO scooterWithDetails = scooterService.getScooterWithDetails(scooterId);

        assertNotNull(scooterWithDetails);
        assertEquals(mockScooter.getCurrentStationId(), scooterWithDetails.getCurrentStationId());
        assertEquals(mockScooter.getCurrentTripId(), scooterWithDetails.getCurrentTripId());
        assertEquals(mockMaintenanceIds, scooterWithDetails.getMaintenanceRecordIds());

        verify(scooterRepository, times(1)).findById(scooterId);
        verify(stationClient, times(1)).getStationById(anyLong());
        verify(maintenanceClient, times(1)).getMaintenanceIdsByScooterId(anyLong());
    }

    @Test
    void testGetAllScooters() {
        // Crear una lista de scooters simulada
        Scooter mockScooter1 = new Scooter();
        mockScooter1.setId(1L);
        mockScooter1.setStatus(true);

        Scooter mockScooter2 = new Scooter();
        mockScooter2.setId(2L);
        mockScooter2.setStatus(false);

        List<Scooter> mockScooters = List.of(mockScooter1, mockScooter2);
        when(scooterRepository.findAll()).thenReturn(mockScooters);

        // Llamamos al método de servicio
        List<ScooterDTO> allScooters = scooterService.getAll();

        // Verificaciones
        assertNotNull(allScooters);
        assertEquals(2, allScooters.size());
        assertEquals(mockScooter1.getId(), allScooters.get(0).getId());
        assertEquals(mockScooter2.getId(), allScooters.get(1).getId());

        // Verificamos que se haya llamado al repositorio
        verify(scooterRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdScooterNotFound() {
        Long scooterId = 999L;

        // Simular que no se encuentra el scooter
        when(scooterRepository.findById(scooterId)).thenReturn(java.util.Optional.empty());

        // Llamamos al método y verificamos la excepción
        assertThrows(EntityNotFoundException.class, () -> scooterService.findById(scooterId));
    }
}


