package com.example.paymentservice.ds;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private double amount;
    private LocalDateTime updateTime;

    public Account(){

    }

    public Account(String name, String email, double amount, LocalDateTime updateTime) {
        this.name = name;
        this.email = email;
        this.amount = amount;
        this.updateTime = updateTime;
    }
}
