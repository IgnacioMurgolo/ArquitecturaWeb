package org.tudai.maintenanceservice.dto;

import lombok.Getter;
import org.tudai.maintenanceservice.entity.Maintenance;

import java.util.Date;

@Getter
public class MaintenanceDTO {

    private Date maintenanceDate;
    private String maintenanceDescription;
    private String maintenanceStatus;
    private Long scooterId;

    public MaintenanceDTO() {
    }

    public MaintenanceDTO(Date maintenanceDate, String maintenanceDescription, String maintenanceStatus, Long scooterId) {
        this.maintenanceDate = maintenanceDate;
        this.maintenanceDescription = maintenanceDescription;
        this.maintenanceStatus = maintenanceStatus;
        this.scooterId = scooterId;
    }

    public MaintenanceDTO(Maintenance newMaintenance) {
        this.maintenanceDate = newMaintenance.getMaintenanceDate();
        this.maintenanceDescription = newMaintenance.getMaintenanceDescription();
        this.maintenanceStatus = newMaintenance.getMaintenanceStatus();
        this.scooterId = newMaintenance.getScooterId();
    }
}
