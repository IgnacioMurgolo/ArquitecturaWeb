package org.tudai.tripservice.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class AccountDTO {
    private double balance;
    private Date creationDate;
    private Boolean active;
    List<Long> usersId;
    List<Long> tripsId;

    public AccountDTO() {}

    public AccountDTO(double balance, Date creationDate, boolean isActive) {
        this.balance = balance;
        this.active = isActive;
        this.creationDate = creationDate;
        this.usersId = new ArrayList<>();
        this.tripsId = new ArrayList<>();
    }


}
