package com.example.transportationservice.dao;

import com.example.transportationservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product,Integer> {
}
