package com.example.affablebeanbackend.dao;

import com.example.affablebeanbackend.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductDao extends CrudRepository<Product,Integer> {
}
