package org.tudai.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AccountDTO {
    private double balance;
    private Date creationDate;
    private Boolean active;
    List<Long> usersId;
    List<Long> tripsId;
    List<UserDTO> usersDTO;

    public AccountDTO() {}

    public AccountDTO(double balance, Date creationDate) {
        this.balance = balance;
        this.creationDate = creationDate;
        this.usersId = new ArrayList<>();
        this.tripsId = new ArrayList<>();
    }

    public AccountDTO(double balance, Date creationDate, List<UserDTO> usersDTO) {
        this.balance = balance;
        this.creationDate = creationDate;
        this.usersDTO = usersDTO;
        this.tripsId = new ArrayList<>();
    }
}
