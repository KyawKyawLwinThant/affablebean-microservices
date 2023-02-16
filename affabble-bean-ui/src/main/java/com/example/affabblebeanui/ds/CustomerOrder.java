package com.example.affabblebeanui.ds;

import lombok.Data;

@Data
public class CustomerOrder {
    private double totalAmount;


    public CustomerOrder(){

    }


    public CustomerOrder(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
