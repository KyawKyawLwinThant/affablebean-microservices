package com.example.affablebeanbackend.dao;

import com.example.affablebeanbackend.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryDao extends CrudRepository<Category,Integer> {
}
