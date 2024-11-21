package org.tudai.tripservice;

import org.bson.Document;
import org.tudai.tripservice.dto.BenefitsBetweenMonthsDTO;
import org.tudai.tripservice.repository.TripRepository;
import org.tudai.tripservice.repository.PauseRepository;
import org.tudai.tripservice.service.TripService;
import org.tudai.tripservice.feign.AccountClient;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.tudai.tripservice.dto.TripDTO;
import org.tudai.tripservice.entitity.Trip;
import org.tudai.tripservice.dto.AccountDTO;
import org.tudai.tripservice.entitity.Pause;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private PauseRepository pauseRepository;

    @Mock
    private AccountClient accountClient;

    @InjectMocks
    private TripService tripService;

    private Trip trip;
    private TripDTO tripDTO;

    @BeforeEach
    void setUp() {
        trip = new Trip("account1", new Date(), new Date(), 100.0, 60.0, 20.0, 1L);
        tripDTO = new TripDTO(trip.getStartDateTime(), trip.getEndDateTime(), trip.getDistanceTraveled(), trip.getDuration(), trip.getCreditsConsumed());
    }

    @Test
    void save_shouldSaveTrip() {
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);

        TripDTO result = tripService.save(tripDTO);

        assertNotNull(result);
        assertEquals(trip.getDistanceTraveled(), result.getDistanceTraveled());
        verify(tripRepository, times(1)).save(any(Trip.class));
    }

    @Test
    void getTripWithAccount_shouldReturnTripWithAccountDetails() {
        AccountDTO accountDTO = new AccountDTO(100.0, new Date(), true);

        when(tripRepository.findById("1")).thenReturn(Optional.of(trip));
        when(accountClient.getAccountById(1L)).thenReturn(accountDTO);

        TripDTO result = tripService.getTripWithAccount("1");

        assertNotNull(result);

        assertEquals(trip.getDistanceTraveled(), result.getDistanceTraveled());

        verify(tripRepository, times(1)).findById("1");
        verify(accountClient, times(1)).getAccountById(1L);
    }

    @Test
    void findTripsByAccountId_shouldReturnTripsForAccount() {
        when(tripRepository.findTripByAccountId("account1")).thenReturn(Collections.singletonList(trip));

        List<TripDTO> result = tripService.findTripsByAccountId("account1");

        assertEquals(1, result.size());
        assertEquals(trip.getAccountId(), result.get(0).getAccountId());
        verify(tripRepository, times(1)).findTripByAccountId("account1");
    }

    @Test
    void setPauseIdToTrip_shouldAddPauseToTrip() {
        when(tripRepository.findById("1")).thenReturn(Optional.of(trip));

        tripService.setPauseIdToTrip("1", "pause1");

        assertTrue(trip.getPausesId().contains("pause1"));
        verify(tripRepository, times(1)).save(trip);
    }

    @Test
    void getPauseTime_shouldCalculateTotalPauseTime() {
        Pause pause1 = new Pause(new Date(1000L), new Date(3000L)); // 2 segundos
        Pause pause2 = new Pause(new Date(5000L), new Date(9000L)); // 4 segundos
        when(pauseRepository.findAllById(anyList())).thenReturn(List.of(pause1, pause2));

        long pauseTime = tripService.getPauseTime(List.of("pause1", "pause2"));

        assertEquals(0, pauseTime); // Tiempo total en minutos (6 segundos son 0 minutos)
    }

    @Test
    void getTripWithPauseTime_shouldReturnTripWithPauseDetails() {
        when(tripRepository.findById("1")).thenReturn(Optional.of(trip));
        when(pauseRepository.findAllById(anyList())).thenReturn(Collections.emptyList()); // Sin pausas

        TripDTO result = tripService.getTripWithPauseTime("1");

        assertNotNull(result);
        assertEquals(0, result.getPauseTime());
        verify(tripRepository, times(1)).findById("1");
        verify(pauseRepository, times(1)).findAllById(anyList());
    }

    @Test
    void deleteById_shouldDeleteTrip() {
        tripService.deleteById("1");

        verify(tripRepository, times(1)).deleteById("1");
    }

    @Test
    void updateById_shouldUpdateTrip() {
        when(tripRepository.findById("1")).thenReturn(Optional.of(trip));

        TripDTO updatedDTO = new TripDTO(new Date(), new Date(), 200.0, 120.0, 40.0);
        TripDTO result = tripService.updateById("1", updatedDTO);

        assertEquals(updatedDTO.getDistanceTraveled(), result.getDistanceTraveled());
        assertEquals(updatedDTO.getCreditsConsumed(), result.getCreditsConsumed());
        verify(tripRepository, times(1)).save(trip);
    }

    @Test
    void countScooterTripByScooterAndYear_shouldReturnCount() {
        when(tripRepository.countTripByScooterAndYear(1L, 2023)).thenReturn(5L);

        Long count = tripService.countScooterTripByScooterAndYear(1L, 2023);

        assertEquals(5, count);
        verify(tripRepository, times(1)).countTripByScooterAndYear(1L, 2023);
    }

    @Test
    void getBenefitsReport_shouldReturnReport() {
        Map<String, Object> doc = Map.of("_id", 3, "totalCredits", 300.0);
        when(tripRepository.getBenefitsReport(2023, 1, 6)).thenReturn(List.of(new Document(doc)));

        List<BenefitsBetweenMonthsDTO> result = tripService.getBenefitsReport(2023, 1, 6);

        assertEquals(1, result.size());
        assertEquals(300.0, result.get(0).getBenefit());
        verify(tripRepository, times(1)).getBenefitsReport(2023, 1, 6);
    }
}
