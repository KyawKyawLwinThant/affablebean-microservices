package com.example.transportationservice.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    @OneToOne
    private CustomerOrderProduct customerOrderProduct;




    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Customer(){

    }
}
