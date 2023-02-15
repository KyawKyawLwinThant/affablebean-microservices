package com.example.transportationservice.service;

import com.example.transportationservice.dao.CustomerOrderProductDao;
import com.example.transportationservice.ds.TransPortInfoRequest;
import com.example.transportationservice.entity.Customer;
import com.example.transportationservice.entity.CustomerOrderProduct;
import com.example.transportationservice.entity.Product;
import com.example.transportationservice.entity.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransportService {

    @Autowired
    private CustomerOrderProductDao orderProductDao;
    @Transactional
    public void saveTransPortService(TransPortInfoRequest request){
        Customer customer=new Customer(request.customerName(),
                request.email());
        List<Product> products= request.products()
                .stream()
                .map(p -> new Product(p.getName(),p.getPrice(),p.getQuantity()))
                .collect(Collectors.toList());




        CustomerOrderProduct orderProduct=new CustomerOrderProduct();
        orderProduct.setOrderId(generateOrderId());
        orderProduct.setTransportTime(LocalDateTime.now());
        orderProduct.setTotalAmount(request.totalAmount());

        for(Product product:products){
            orderProduct.addProduct(product);
        }

        orderProduct.addCustomer(customer);
        orderProductDao.save(orderProduct);

    }
    private String generateOrderId(){

        return UUID.randomUUID().toString()
                .replace("-","");
    }
}
