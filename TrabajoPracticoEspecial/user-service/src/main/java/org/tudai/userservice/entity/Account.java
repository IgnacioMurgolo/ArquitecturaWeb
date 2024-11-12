package org.tudai.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double balance;
    private Date creationDate;
    private Boolean active;
    @ElementCollection
    private List<Long> usersId;
    @ElementCollection
    private List<Long> tripsId;

    public Account() {}

    public Account(double balance, Date creationDate) {
        this.balance = balance;
        this.creationDate = creationDate;
        this.active = true;
        this.usersId = new ArrayList<>();
        this.tripsId = new ArrayList<>();
    }
}
