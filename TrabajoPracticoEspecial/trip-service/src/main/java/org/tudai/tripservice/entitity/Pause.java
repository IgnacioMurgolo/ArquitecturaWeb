package org.tudai.tripservice.entitity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "pauses")
@Data
public class Pause {
    @Id
    private String id;
    private Date startPause;
    private Date endPause;
    private Boolean exceededTime;
    private String tripId;

    public Pause(Date startPause, Date endPause, Boolean exceededTime, String tripId) {
        this.startPause = startPause;
        this.endPause = endPause;
        this.exceededTime = exceededTime;
        this.tripId = tripId;
    }

    public Pause() {}

    public Pause(Date date, Date date1) {
        this.startPause = date;
        this.endPause = date1;
    }
}
