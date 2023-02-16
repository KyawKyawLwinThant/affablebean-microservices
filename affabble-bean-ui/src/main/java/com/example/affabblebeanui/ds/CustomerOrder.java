package com.example.affabblebeanui.ds;

import lombok.Data;

@Data
public class CustomerOrder {
    private double totalAmount;
    private String orderId;

    public CustomerOrder(){

    }


    public CustomerOrder(double totalAmount, String orderId) {
        this.totalAmount = totalAmount;
        this.orderId = orderId;
    }
}
