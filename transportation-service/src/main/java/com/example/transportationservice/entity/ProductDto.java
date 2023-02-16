package com.example.transportationservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDto {

    private String orderId;
    private String name;
    private double price;
    private int quantity;

    public ProductDto(){

    }

    public ProductDto(String orderId, String name, double price, int quantity) {
        this.orderId = orderId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
