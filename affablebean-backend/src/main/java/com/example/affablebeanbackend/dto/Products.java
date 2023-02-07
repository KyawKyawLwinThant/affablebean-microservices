package com.example.affablebeanbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
public class Products {
    private List<Product> products;

    public Products(){

    }
    public Products(List<Product> products){
        this.products=products;
    }


}
