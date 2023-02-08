package com.example.affabblebeanui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record Product(int id,
                      String name,
                      String description,
                      @JsonProperty("last_update") LocalDateTime lastUpdate,
                      double price,
                      String categoryName) {
}
