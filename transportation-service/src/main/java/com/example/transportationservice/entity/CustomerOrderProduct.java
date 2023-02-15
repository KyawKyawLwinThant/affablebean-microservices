package com.example.transportationservice.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class CustomerOrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String orderId;
    private LocalDateTime transportTime;
    private double totalAmount;
    @OneToOne(mappedBy = "customerOrderProduct"
            ,cascade = CascadeType.PERSIST)
    private Customer customer;
    @OneToMany(mappedBy = "customerOrderProduct"
            ,cascade = CascadeType.PERSIST)
    private List<Product> products=new ArrayList<>();

    public void addCustomer(Customer customer){
        customer.setCustomerOrderProduct(this);
        setCustomer(customer);
    }

    public void addProduct(Product product){
        product.setCustomerOrderProduct(this);
        products.add(product);
    }
}
