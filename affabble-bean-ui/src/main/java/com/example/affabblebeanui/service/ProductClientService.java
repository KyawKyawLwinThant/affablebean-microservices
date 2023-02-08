package com.example.affabblebeanui.service;

import com.example.affabblebeanui.dto.Product;
import com.example.affabblebeanui.dto.Products;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductClientService {

    private RestTemplate template=new RestTemplate();
    private List<Product> products;
    @PostConstruct
    public void init(){
        ResponseEntity<Products> response =template
                .getForEntity("http://localhost:8080/backend/products"
                        , Products.class);
        if (response.getStatusCode().is2xxSuccessful()){
            this.products=
                    response.getBody().getProducts();
            System.out.println(products);
        }
        else
            throw new RuntimeException("Error!");
    }
    public List<Product> findProductByCategoryName(String categoryName){
        return products.stream()
                .filter( p -> p.categoryName().equals(categoryName))
                .collect(Collectors.toList());
    }

    public List<Product> findAllProducts(){
        return this.products;
    }


}
