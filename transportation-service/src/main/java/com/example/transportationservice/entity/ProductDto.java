package com.example.transportationservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDto {

    private int id;
    private String name;
    private String description;
    private @JsonProperty("last_update") LocalDateTime lastUpdate;
    private double price;
    private String categoryName;
    private int quantity;
}
