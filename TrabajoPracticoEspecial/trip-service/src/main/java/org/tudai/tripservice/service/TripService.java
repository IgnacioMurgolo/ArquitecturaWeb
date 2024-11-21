package org.tudai.tripservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tudai.tripservice.dto.BenefitsBetweenMonthsDTO;
import org.tudai.tripservice.dto.TripDTO;
import org.tudai.tripservice.entitity.Pause;
import org.tudai.tripservice.entitity.Trip;
import org.tudai.tripservice.feign.AccountClient;
import org.tudai.tripservice.repository.CustomTripRepositoryImpl;
import org.tudai.tripservice.repository.PauseRepository;
import org.tudai.tripservice.repository.TripRepository;
import org.tudai.tripservice.dto.AccountDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                tripDTO.getDistanceTraveled(), tripDTO.getDuration(), tripDTO.getCreditsConsumed(), tripDTO.getScooterId());
        newTrip = tripRepository.save(newTrip);
        return convertToDTO(newTrip);
    }

    @Transactional
    public TripDTO getTripWithAccount(String tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found with id " + tripId));
        AccountDTO account = accountClient.getAccountById(Long.valueOf(trip.getAccountId()));

        return new TripDTO(trip.getStartDateTime(), trip.getEndDateTime(), trip.getDistanceTraveled(), trip.getDuration(), trip.getCreditsConsumed());
    }

    @Transactional
    public List<TripDTO> findTripsByAccountId(String accountId) {
        List<Trip> trips = tripRepository.findTripByAccountId(accountId);

        // Mapea cada Trip a TripDTO
        return trips.stream()
                .map(trip -> new TripDTO(
                        trip.getStartDateTime(),
                        trip.getEndDateTime(),
                        trip.getDistanceTraveled(),
                        trip.getDuration(),
                        trip.getCreditsConsumed(),
                        trip.getAccountId(),
                        trip.getScooterId()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void setPauseIdToTrip(String tripId, String pauseId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new EntityNotFoundException("Trip not found with id " + tripId));
        trip.getPausesId().add(pauseId);
        tripRepository.save(trip);  // Actualizamos el viaje con el nuevo ID de pausa
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

    public TripDTO getTripWithPauseTime(String tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new EntityNotFoundException("Trip not found with id " + tripId));

        long pauseTime = getPauseTime(trip.getPausesId());

        //  Creamos el DTO con el tiempo de pausa
        TripDTO tripDTO = convertToDTO(trip);
        tripDTO.setPauseTime(pauseTime);

        return tripDTO;
    }

    // Método para calcular el tiempo total de pausa de una lista de pausas
    public long getPauseTime(List<String> pausesId) {
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
    public void deleteById(String tripId) {
        tripRepository.deleteById(tripId);
    }

    @Transactional
    public TripDTO updateById(String id, TripDTO tripDTO) {
        Trip tripUpdate = tripRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Trip not found with id " + id));
        tripUpdate.setStartDateTime(tripDTO.getStartDateTime());
        tripUpdate.setEndDateTime(tripDTO.getEndDateTime());
        tripUpdate.setDistanceTraveled(tripDTO.getDistanceTraveled());
        tripUpdate.setDuration(tripDTO.getDuration());
        tripUpdate.setCreditsConsumed(tripDTO.getCreditsConsumed());

        tripRepository.save(tripUpdate);
        return convertToDTO(tripUpdate);
    }


    private TripDTO convertToDTO(Trip trip) {
        // Convierte Trip a TripDTO
        TripDTO newTripDTO = new TripDTO();
        newTripDTO.setAccountId(trip.getAccountId());
        newTripDTO.setStartDateTime(trip.getStartDateTime());
        newTripDTO.setEndDateTime(trip.getEndDateTime());
        newTripDTO.setDistanceTraveled(trip.getDistanceTraveled());
        newTripDTO.setDuration(trip.getDuration());
        newTripDTO.setCreditsConsumed(trip.getCreditsConsumed());

        return new TripDTO();
    }

    public List<Pause> getPausesForTrip(Trip trip) {
        return pauseRepository.findAllById(trip.getPausesId());
    }

    public Long countScooterTripByScooterAndYear(Long scooterId, int year) {
        return tripRepository.countTripByScooterAndYear(scooterId, year);
    }

    public List<BenefitsBetweenMonthsDTO> getBenefitsReport(int year, int startMonth, int endMonth) {
        List<Document> rawResults = tripRepository.getBenefitsReport(year, startMonth, endMonth);

        return rawResults.stream()
                .map(doc -> new BenefitsBetweenMonthsDTO(
                        doc.getInteger("_id"),  // Mongo usa "_id" como agrupador
                        doc.getDouble("totalCredits")
                ))
                .collect(Collectors.toList());
    }
}
