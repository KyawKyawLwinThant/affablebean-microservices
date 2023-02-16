package com.example.transportationservice.ds;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class CustomerOrder {
    private double totalAmount;
    private String orderId;


    public CustomerOrder(double totalAmount, String orderId) {
        this.totalAmount = totalAmount;
        this.orderId = orderId;
    }
}
