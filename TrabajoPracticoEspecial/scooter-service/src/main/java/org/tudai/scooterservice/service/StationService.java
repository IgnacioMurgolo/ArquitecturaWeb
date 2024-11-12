package org.tudai.scooterservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tudai.scooterservice.dto.StationDTO;
import org.tudai.scooterservice.entity.Station;
import org.tudai.scooterservice.repository.StationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationService {
    private final StationRepository stationRepository;

    @Autowired
    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Transactional
    public StationDTO save(StationDTO stationDTO) {
        Station station = new Station();
        station.setName(stationDTO.getName());
        station.setUbication(stationDTO.getUbication());
        station.setScootersId(stationDTO.getScootersId());
        station = stationRepository.save(station);
        return new StationDTO(station);
    }

    @Transactional
    public void addScooterToStation(Long stationId, Long scooterId) {
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new EntityNotFoundException("Station not found with id " + stationId));

        // Agregar el scooterId a la lista de scooters de la estación
        if (!station.getScootersId().contains(scooterId)) {
            station.getScootersId().add(scooterId);
            stationRepository.save(station);
        }
    }

    @Transactional
    public StationDTO findById(Long id) {
        return stationRepository.findById(id).map(this::convertToDto)
                .orElseThrow(() -> new EntityNotFoundException("Station not found with id " + id));
    }

    @Transactional
    public List<StationDTO> getAll() {
        List<Station> stations = stationRepository.findAll();
        List<StationDTO> stationDTOs = new ArrayList<>();
        for (Station station : stations) {
            stationDTOs.add(convertToDto(station));
        }
        return stationDTOs;
    }

    @Transactional
    public void deleteById(Long id) {
        stationRepository.deleteById(id);
    }

    @Transactional
    public StationDTO updateById(Long id, StationDTO stationDTO) {
       Station stationUpdate = stationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Station not found with id " + id));
       stationUpdate.setName(stationDTO.getName());
       stationUpdate.setUbication(stationDTO.getUbication());
       stationUpdate.setScootersId(stationDTO.getScootersId());

       stationUpdate = stationRepository.save(stationUpdate);
       return new StationDTO(stationUpdate);
    }

    private StationDTO convertToDto(Station station) {
        return new StationDTO(station);
    }
}
