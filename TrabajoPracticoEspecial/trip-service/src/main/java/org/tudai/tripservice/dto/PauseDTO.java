package org.tudai.tripservice.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class PauseDTO {
    private Date startPause;
    private Date endPause;
    private Boolean exceededTime;
    private Long tripId;

    public PauseDTO(Date startPause, Date endPause, Boolean exceededTime, Long tripId) {
        this.startPause = startPause;
        this.endPause = endPause;
        this.exceededTime = exceededTime;
        this.tripId = tripId;
    }

    public PauseDTO() {}
}
