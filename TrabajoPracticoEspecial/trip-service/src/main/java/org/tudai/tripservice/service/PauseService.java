package org.tudai.tripservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tudai.tripservice.dto.PauseDTO;
import org.tudai.tripservice.entitity.Pause;
import org.tudai.tripservice.repository.PauseRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PauseService {
    private final PauseRepository pauseRepository;
    private final TripService tripService;

    @Autowired
    public PauseService(PauseRepository pauseRepository, TripService tripService) {
        this.pauseRepository = pauseRepository;
        this.tripService = tripService;
    }

    @Transactional
    public void save(PauseDTO pauseDTO) {
        Pause newPause = new Pause(pauseDTO.getStartPause(), pauseDTO.getEndPause(), pauseDTO.getExceededTime(), pauseDTO.getTripId());
        pauseRepository.save(newPause);
        tripService.setPauseIdToTrip(newPause.getId(), newPause.getTripId());
    }

    @Transactional
    public List<PauseDTO> getAll() {
        List<PauseDTO> pauses = new ArrayList<>();
        List<Pause> pauseList = pauseRepository.findAll();
        for (Pause pause : pauseList) {
            PauseDTO pauseDTO = convertToDTO(pause);
            pauses.add(pauseDTO);
        }
        return pauses;
    }

    @Transactional
    public void deleteById(Long pauseId) {
        pauseRepository.deleteById(pauseId);
    }

    @Transactional
    public void updateById(Long id, PauseDTO pauseDTO) {
        Pause pauseUpdate = pauseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pause not found with id " + id));
        pauseUpdate.setStartPause(pauseDTO.getStartPause());
        pauseUpdate.setEndPause(pauseDTO.getEndPause());
        pauseUpdate.setExceededTime(pauseDTO.getExceededTime());
        pauseUpdate.setTripId(pauseDTO.getTripId());

        pauseRepository.save(pauseUpdate);
        convertToDTO(pauseUpdate);
    }


    private PauseDTO convertToDTO(Pause pause) {
        return new PauseDTO(pause.getStartPause(), pause.getEndPause(), pause.getExceededTime(), pause.getTripId());
    }

}
