package com.example.affabblebeanui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Product{
    private int id;
    private String name;
    private String description;
    private @JsonProperty("last_update") LocalDateTime lastUpdate;
    private double price;
    private String categoryName;
    private int quantity;
    public Product(){

    }

    public Product(int id, String name, String description, LocalDateTime lastUpdate, double price, String categoryName, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lastUpdate = lastUpdate;
        this.price = price;
        this.categoryName = categoryName;
        this.quantity = quantity;
    }
}

