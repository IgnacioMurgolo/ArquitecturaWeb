package org.tudai.tripservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tudai.tripservice.dto.TripDTO;
import org.tudai.tripservice.entitity.Pause;
import org.tudai.tripservice.entitity.Trip;
import org.tudai.tripservice.feign.AccountClient;
import org.tudai.tripservice.repository.PauseRepository;
import org.tudai.tripservice.repository.TripRepository;
import org.tudai.userservice.dto.AccountDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final AccountClient accountClient;
    private final PauseRepository pauseRepository;

    @Autowired
    public TripService(TripRepository tripRepository, AccountClient accountClient, PauseRepository pauseRepository) {
        this.tripRepository = tripRepository;
        this.accountClient = accountClient;
        this.pauseRepository = pauseRepository;
    }

    @Transactional
    public TripDTO save(TripDTO tripDTO) {
        Trip newTrip = new Trip(tripDTO.getAccountId(), tripDTO.getStartDateTime(), tripDTO.getEndDateTime(),
                tripDTO.getDistanceTraveled(), tripDTO.getDuration(), tripDTO.getCreditsConsumed());
        newTrip = tripRepository.save(newTrip);
        return new TripDTO(newTrip);
    }

    @Transactional
    public TripDTO getTripWithAccount(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found with id " + tripId));
        AccountDTO account = accountClient.getAccountById(trip.getAccountId());

        return new TripDTO(trip.getStartDateTime(), trip.getEndDateTime(), trip.getDistanceTraveled(), trip.getDuration(), trip.getCreditsConsumed(), account);
    }

    @Transactional
    public List<TripDTO> findTripsByAccountId(Long accountId) {
        AccountDTO account = accountClient.getAccountById(accountId);
        List<TripDTO> result = tripRepository.getTripsByAccountId(accountId);
        for (TripDTO trip : result) {
            trip.setAccount(account);
            result.add(trip);
        }
        return result;
    }

    @Transactional
    public void setPauseIdToTrip(Long tripId, Long pauseId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new EntityNotFoundException("Trip not found with id " + tripId));
        trip.getPausesId().add(pauseId);
    }

    @Transactional
    public List<TripDTO> getAll() {
        List<TripDTO> trips = new ArrayList<>();
        List<Trip> tripList = tripRepository.findAll();
        for (Trip trip : tripList) {
            TripDTO tripDTO = convertToDTO(trip);
            trips.add(tripDTO);
        }
        return trips;
    }

    public TripDTO getTripWithPauseTime(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found with id " + tripId));

        long pauseTime = getPauseTime(trip.getPausesId());

        // Crear un TripDTO con el tiempo de pausa incluido
        TripDTO tripDTO = new TripDTO(trip);
        tripDTO.setPauseTime(pauseTime);

        return tripDTO;
    }

    // Método para calcular el tiempo total de pausa de una lista de pausas
    public long getPauseTime(List<Long> pausesId) {
        long totalPauseTime = 0;

        // Obtener los detalles de cada pausa a partir de los IDs
        List<Pause> pauses = pauseRepository.findAllById(pausesId);

        for (Pause pause : pauses) {
            if (pause.getStartPause() != null && pause.getEndPause() != null) {
                // Calcular la duración de la pausa en milisegundos
                long pauseDuration = pause.getEndPause().getTime() - pause.getStartPause().getTime();
                totalPauseTime += pauseDuration;
            }
        }
        // Convertir el total de pausa a minutos (o a la unidad deseada)
        long totalPauseTimeInMinutes = totalPauseTime / (1000 * 60);
        return totalPauseTimeInMinutes;
    }

    @Transactional
    public void deleteById(Long tripId) {
        tripRepository.deleteById(tripId);
    }

    @Transactional
    public TripDTO updateById(Long id, TripDTO tripDTO) {
        Trip tripUpdate = tripRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Trip not found with id " + id));
        tripUpdate.setStartDateTime(tripDTO.getStartDateTime());
        tripUpdate.setEndDateTime(tripDTO.getEndDateTime());
        tripUpdate.setDistanceTraveled(tripDTO.getDistanceTraveled());
        tripUpdate.setDuration(tripDTO.getDuration());
        tripUpdate.setCreditsConsumed(tripDTO.getCreditsConsumed());

        tripRepository.save(tripUpdate);
        return new TripDTO(tripUpdate);
    }


    private TripDTO convertToDTO(Trip trip) {
        // Convierte Trip a TripDTO
        return new TripDTO(trip);
    }

}
