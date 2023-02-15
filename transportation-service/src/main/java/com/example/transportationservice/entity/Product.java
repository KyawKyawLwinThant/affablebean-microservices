package com.example.transportationservice.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/*
    name
    quantity
    price
    totalMount
    userEmail
    userName
    userId
    LocalDateTime
    orderId
 */
@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double price;
    private int quantity;
    @ManyToOne
    private CustomerOrderProduct customerOrderProduct;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(){

    }
}
