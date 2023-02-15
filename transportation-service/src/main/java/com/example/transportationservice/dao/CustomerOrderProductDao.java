package com.example.transportationservice.dao;

import com.example.transportationservice.entity.CustomerOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderProductDao extends JpaRepository<CustomerOrderProduct,Integer> {
}
