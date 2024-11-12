package org.tudai.maintenanceservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tudai.maintenanceservice.dto.MaintenanceDTO;
import org.tudai.maintenanceservice.dto.ScooterDTO;
import org.tudai.maintenanceservice.entity.Maintenance;
import org.tudai.maintenanceservice.feign.ScooterClient;
import org.tudai.maintenanceservice.repository.MaintenanceRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final ScooterClient scooterClient;

    @Autowired
    public MaintenanceService(MaintenanceRepository maintenanceRepository, ScooterClient scooterClient) {
        this.maintenanceRepository = maintenanceRepository;
        this.scooterClient = scooterClient;
    }

    @Transactional
    public MaintenanceDTO save(MaintenanceDTO maintenance) {
        Maintenance newMaintenance = new Maintenance();
        newMaintenance.setMaintenanceDate(maintenance.getMaintenanceDate());
        newMaintenance.setMaintenanceDescription(maintenance.getMaintenanceDescription());
        newMaintenance.setMaintenanceStatus(maintenance.getMaintenanceStatus());
        newMaintenance.setScooterId(maintenance.getScooterId());

        newMaintenance = maintenanceRepository.save(newMaintenance);
        return new MaintenanceDTO(newMaintenance);
    }

    @Transactional
    public void deleteById(Long id) {
        maintenanceRepository.deleteById(id);
    }

    @Transactional
    public List<MaintenanceDTO> findAll() {
        List<Maintenance> maintenances = maintenanceRepository.findAll();
        List<MaintenanceDTO> maintenanceDTOs = new ArrayList<>();
        for (Maintenance maintenance : maintenances) {
            MaintenanceDTO maintenanceDTO = convertToDTO(maintenance);
            maintenanceDTOs.add(maintenanceDTO);
        }
        return maintenanceDTOs;
    }

    @Transactional
    public MaintenanceDTO findById(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Maintenance not found with id " + id));
        return convertToDTO(maintenance);
    }

    @Transactional
    public List<Long> getMaintenanceIdsByScooterId(Long scooterId) {
        return maintenanceRepository.getMaintenanceIdsByScooterId(scooterId);
    }

    @Transactional
    public void assignScooterToMaintenance(Long maintenanceId, Long scooterId) {
        ScooterDTO scooterDTO = scooterClient.findById(scooterId);
        if (scooterDTO == null) {
            throw new EntityNotFoundException("Scooter not found with id " + scooterId);
        }
        Maintenance maintenance = maintenanceRepository.findById(maintenanceId).orElseThrow(() -> new EntityNotFoundException("Maintenance not found with id " + maintenanceId));
        maintenance.setScooterId(scooterId);
        maintenance.setMaintenanceDate(new Date());
        maintenance.setMaintenanceStatus("en mantenimiento");
        scooterClient.onMaintenance(scooterId);
        maintenanceRepository.save(maintenance);
    }

    @Transactional
    public void endMaintenance(Long maintenanceId) {
        Maintenance maintenance = maintenanceRepository.findById(maintenanceId).orElseThrow(() -> new EntityNotFoundException("Maintenance not found with id " + maintenanceId));
        maintenance.setMaintenanceStatus("completado");
        Long scooterId = maintenance.getScooterId();
        scooterClient.endMaintenance(scooterId);
        maintenanceRepository.save(maintenance);

    }

    public MaintenanceDTO convertToDTO(Maintenance maintenance) {
        return new MaintenanceDTO(maintenance.getMaintenanceDate(),
                maintenance.getMaintenanceDescription(), maintenance.getMaintenanceStatus(),
                maintenance.getScooterId());
    }


}
