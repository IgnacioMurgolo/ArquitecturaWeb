package org.tudai.scooterservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tudai.scooterservice.dto.ScooterDTO;
import org.tudai.scooterservice.dto.StationDTO;
import org.tudai.scooterservice.entity.Scooter;
import org.tudai.scooterservice.feign.MaintenanceClient;
import org.tudai.scooterservice.feign.StationClient;
import org.tudai.scooterservice.repository.ScooterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScooterService {
    private final ScooterRepository scooterRepository;
    private final StationClient stationClient;
    private final MaintenanceClient maintenanceClient;

    @Autowired
    public ScooterService(ScooterRepository scooterRepository, StationClient stationClient, MaintenanceClient maintenanceClient) {
        this.scooterRepository = scooterRepository;
        this.stationClient = stationClient;
        this.maintenanceClient = maintenanceClient;
    }

    @Transactional
    public ScooterDTO parkScooterAtStation(Long scooterId, Long stationId) {
        Scooter scooter = scooterRepository.findById(scooterId)
                .orElseThrow(() -> new EntityNotFoundException("Scooter not found with id " + scooterId));

        // Actualizar la estación actual del scooter
        scooter.setCurrentStationId(stationId);
        scooter.setStatus(true); // Cambiar el estado a "disponible" cuando está estacionado
        scooterRepository.save(scooter);

        // Llamar a StationService para agregar el scooterId a la estación
        stationClient.addScooterToStation(stationId, scooterId);

        return convertToDto(scooter);
    }

    @Transactional
    public ScooterDTO getScooterWithDetails(Long scooterId) {
        Scooter scooter = scooterRepository.findById(scooterId)
                .orElseThrow(() -> new EntityNotFoundException("Scooter not found with id " + scooterId));

        // Llama a StationService para obtener detalles de la estación
        StationDTO station = stationClient.getStationById(scooter.getCurrentStationId());

        // Llama a MaintenanceService para obtener los IDs de registros de mantenimiento
        List<Long> maintenanceIds = maintenanceClient.getMaintenanceIdsByScooterId(scooterId);

        // Convertir Scooter a DTO y agregar información de la estación y los registros de mantenimiento
        ScooterDTO scooterDTO = convertToDto(scooter);
        scooterDTO.setCurrentStationId(scooter.getCurrentStationId());
        scooterDTO.setCurrentTripId(scooter.getCurrentTripId());
        scooterDTO.setMaintenanceRecordIds(maintenanceIds); // Agrega los IDs de mantenimiento

        return scooterDTO;
    }

    @Transactional
    public ScooterDTO save(ScooterDTO scooterDTO) {
        Scooter newScooter = new Scooter();
        newScooter.setStatus(scooterDTO.isStatus());
        newScooter.setUbication(scooterDTO.getUbication());
        newScooter.setHoursUsed(scooterDTO.getHoursUsed());
        newScooter.setKilometersTraveled(scooterDTO.getKilometersTraveled());

        newScooter = scooterRepository.save(newScooter);
        return convertToDto(newScooter);
    }

    @Transactional
    public List<ScooterDTO> getAll() {
        List<ScooterDTO> result = new ArrayList<>();
        List<Scooter> scooters = scooterRepository.findAll();
        for (Scooter scooter : scooters) {
            ScooterDTO dto = convertToDto(scooter);
            result.add(dto);
        }
        return result;
    }
    // cuenta cuantos scooters hay con status true
    public Long countOperationalScooters() {
        return scooterRepository.countByStatus(true);
    }

    public Long countMaintenanceScooters() {
        return scooterRepository.countByStatus(false);
    }

    @Transactional
    public ScooterDTO findById(Long id) {
        return scooterRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("Scooter no encontrado con id " + id));
    }

    @Transactional
    public void deleteById(Long id) {
        scooterRepository.deleteById(id);
    }

    @Transactional
    public ScooterDTO updateById(Long id, ScooterDTO scooterDTO){
        Scooter scooterUpdate = scooterRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Scooter not found with id " + id));
        scooterUpdate.setUbication(scooterDTO.getUbication());
        scooterUpdate.setHoursUsed(scooterDTO.getHoursUsed());

        scooterUpdate = scooterRepository.save(scooterUpdate);
        return convertToDto(scooterUpdate);
    }

    private ScooterDTO convertToDto(Scooter scooter) {
        ScooterDTO dto = new ScooterDTO();
        dto.setStatus(scooter.isStatus());
        dto.setUbication(scooter.getUbication());
        dto.setHoursUsed(scooter.getHoursUsed());
        dto.setKilometersTraveled(scooter.getKilometersTraveled());
        dto.setCurrentStationId(scooter.getCurrentStationId());
        dto.setCurrentTripId(scooter.getCurrentTripId());
        dto.setMaintenanceRecordIds(scooter.getMaintenanceIds());

        return dto;
    }

    public List<ScooterDTO> findNearbyScooters(String ubicacion) {
        // Busca scooters cuyas ubicaciones contengan la cadena de ubicación solicitada
        List<Scooter> scooters = scooterRepository.findByUbicationContainingIgnoreCase(ubicacion);

        // Convertir los scooters encontrados a DTOs
        return scooters.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


}
