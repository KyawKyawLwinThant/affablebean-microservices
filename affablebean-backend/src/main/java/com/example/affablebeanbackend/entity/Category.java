package com.example.affablebeanbackend.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Category {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Product> productList=
            new ArrayList<>();



    public Category(){

    }
    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
