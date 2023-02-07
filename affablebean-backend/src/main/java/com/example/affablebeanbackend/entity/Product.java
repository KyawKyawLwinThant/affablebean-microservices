package com.example.affablebeanbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @Column(name="price")
    private double price;
    @Column(name="description")
    private String description;
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;
    @Transient
    private String date;
    @Transient
    private int categoryId;

    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
