package com.example.affabblebeanui.service;

import com.example.affabblebeanui.dto.Product;
import com.example.affabblebeanui.dto.Products;
import com.example.affabblebeanui.exception.ProductNotFoundException;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
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
    public Product findProductById(int id){
        return products
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(()->new
                        ProductNotFoundException(HttpStatus.NOT_FOUND
                        ,id +" NotFound!"));
    }
    public List<Product> findProductByCategoryName(String categoryName){
        return products.stream()
                .filter( p -> p.getCategoryName().equals(categoryName))
                .collect(Collectors.toList());
    }

    public List<Product> findAllProducts(){
        return this.products;
    }

    record TransferRequest(
            @JsonProperty("from_name") String fromName,
            @JsonProperty("from_email") String fromEmail,
            @JsonProperty("to_name") String toName,
            @JsonProperty("to_email") String toEmail,
            double amount
    ){

    }
    public void checkout(String name, String email, double total) {
        var request=new TransferRequest(
                name,
                email,
                "william",
                "william@gmail.com",
                total
        );
        ResponseEntity<String> response=template
                .postForEntity("http://localhost:8060/payment/transfer",request,String.class);
        if(!response.getStatusCode().is2xxSuccessful()){
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
        }
        else {
            System.out.println("Successfully Checkout..............");
        }

    }
}
