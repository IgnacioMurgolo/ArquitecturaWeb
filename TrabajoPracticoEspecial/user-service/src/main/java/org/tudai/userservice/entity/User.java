package org.tudai.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app-user")
@Data
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String name;
    private String lastName;
    private int phoneNumber;
    private String email;
    @ElementCollection
    private List<Long> accountsId;

    public User() {}

    public User(String userName, String name, String lastName, int phoneNumber, String email) {
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.accountsId = new ArrayList<>();
    }

}
