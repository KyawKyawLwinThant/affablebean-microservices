package com.example.affablebeanbackend.service;

import com.example.affablebeanbackend.dao.ProductDao;
import com.example.affablebeanbackend.dto.Product;
import com.example.affablebeanbackend.dto.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProductDao productDao;

    public ProductService(DataSource dataSource){
        jdbcTemplate=new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
    }

    public void saveProduct(int id, String name, double price
            , String description, String lastUpdate,int categoryId){
        jdbcTemplate.update(
                "insert into product(id,description,last_update,name,price,category_id)" +
                        "values(?,?,?,?,?,?)",
                        id,description,convertToLocalDateTime(lastUpdate),
                        name,price,categoryId
                );
    }

    public LocalDateTime convertToLocalDateTime(String date){
        String[] sdate=date.split(" ");
        StringBuilder sb=new StringBuilder();
        sb.append(sdate[1]).append("T").append(sdate[2]);
        return LocalDateTime.parse(sb.toString());
    }

    public Products listAllProducts(){
        return new Products(StreamSupport
                .stream(productDao.findAll()
                        .spliterator(),false)
                .map(p -> toDto(p))
                .collect(Collectors.toList()));

    }


    private Product toDto(com.example.affablebeanbackend.entity.Product product){
        return new Product(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getLastUpdate(),
                product.getPrice(),
                product.getCategory().getName()
        );
    }

}
