package com.example.transportationservice.ds;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class CustomerOrder {
    private double totalAmount;

    public CustomerOrder(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
