package org.tudai.scooterservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.tudai.scooterservice.dto.StationDTO;
import org.tudai.scooterservice.entity.Station;
import org.tudai.scooterservice.repository.StationRepository;
import org.tudai.scooterservice.service.StationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class StationTestService {
    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private StationService stationService;

    private Station station;
    private StationDTO stationDTO;

    @BeforeEach
    void setUp() {
        station = new Station();
        station.setId(1L);
        station.setName("Main Station");
        station.setUbication("Downtown");
        station.setScootersId(new ArrayList<>(List.of(100L, 101L)));

        stationDTO = new StationDTO();
        stationDTO.setName("Main Station");
        stationDTO.setUbication("Downtown");
        stationDTO.setScootersId(new ArrayList<>(List.of(100L, 101L)));
    }

    @Test
    void testSave() {
        when(stationRepository.save(any(Station.class))).thenReturn(station);

        StationDTO savedStation = stationService.save(stationDTO);

        assertNotNull(savedStation);
        assertEquals(stationDTO.getName(), savedStation.getName());
        assertEquals(stationDTO.getUbication(), savedStation.getUbication());
        assertEquals(stationDTO.getScootersId(), savedStation.getScootersId());
        verify(stationRepository, times(1)).save(any(Station.class));
    }

    @Test
    void testAddScooterToStation() {
        Long scooterId = 102L;

        when(stationRepository.findById(1L)).thenReturn(Optional.of(station));
        when(stationRepository.save(any(Station.class))).thenReturn(station);

        stationService.addScooterToStation(1L, scooterId);

        assertTrue(station.getScootersId().contains(scooterId));
        verify(stationRepository, times(1)).findById(1L);
        verify(stationRepository, times(1)).save(station);
    }

    @Test
    void testFindById() {
        when(stationRepository.findById(1L)).thenReturn(Optional.of(station));

        StationDTO foundStation = stationService.findById(1L);

        assertNotNull(foundStation);
        assertEquals(station.getName(), foundStation.getName());
        assertEquals(station.getUbication(), foundStation.getUbication());
        assertEquals(station.getScootersId(), foundStation.getScootersId());
        verify(stationRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAll() {
        List<Station> stations = List.of(station);
        when(stationRepository.findAll()).thenReturn(stations);

        List<StationDTO> stationDTOs = stationService.getAll();

        assertNotNull(stationDTOs);
        assertEquals(1, stationDTOs.size());
        assertEquals(station.getName(), stationDTOs.get(0).getName());
        verify(stationRepository, times(1)).findAll();
    }

    @Test
    void testDeleteById() {
        doNothing().when(stationRepository).deleteById(1L);

        stationService.deleteById(1L);

        verify(stationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateById() {
        when(stationRepository.findById(1L)).thenReturn(Optional.of(station));
        when(stationRepository.save(any(Station.class))).thenReturn(station);

        stationDTO.setName("Updated Station");
        stationDTO.setUbication("New Location");
        StationDTO updatedStation = stationService.updateById(1L, stationDTO);

        assertNotNull(updatedStation);
        assertEquals("Updated Station", updatedStation.getName());
        assertEquals("New Location", updatedStation.getUbication());
        verify(stationRepository, times(1)).findById(1L);
        verify(stationRepository, times(1)).save(station);
    }
}
