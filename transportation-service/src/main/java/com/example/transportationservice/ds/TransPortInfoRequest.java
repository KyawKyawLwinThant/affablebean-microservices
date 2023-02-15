package com.example.transportationservice.ds;

import com.example.transportationservice.entity.ProductDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public  record TransPortInfoRequest(
        @JsonProperty("customer_name") String customerName,
        String email,
        List<ProductDto> products,
        @JsonProperty("total_amount")
        double totalAmount
){

}
