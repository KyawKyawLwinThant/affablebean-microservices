package com.example.affabblebeanui.ds;

import lombok.Data;

@Data
public class ProductDto {

    private String orderId;
    private String name;
    private double price;
    private int quantity;
}
