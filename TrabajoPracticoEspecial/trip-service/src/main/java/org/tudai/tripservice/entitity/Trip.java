package org.tudai.tripservice.entitity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "trips")
@Data
public class Trip {
    @Id
    private String id;
    private String accountId; // PARA LA RELACION CON Account
    private Date startDateTime;
    private Date endDateTime;
    private Double distanceTraveled;
    private Double duration;
    private Double creditsConsumed;
    private Long scooterId;
    private List<String> pausesId; // PARA LA RELACION CON Pause

    public Trip(String accountId, Date startDateTime, Date endDateTime, Double distanceTraveled, Double duration, Double creditsConsumed, Long scooterId) {
        this.accountId = accountId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.distanceTraveled = distanceTraveled;
        this.duration = duration;
        this.creditsConsumed = creditsConsumed;
        this.scooterId = scooterId;
        this.pausesId = new ArrayList<>();
    }
}
