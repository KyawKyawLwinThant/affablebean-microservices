package com.example.transportationservice.ds;

import com.example.transportationservice.entity.Product;
import com.example.transportationservice.entity.ProductDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public  class TransPortInfoResponse{

        @JsonProperty("customer_name") private Set<String> customerName;
        private String email;
        private List<ProductDto> products;
        @JsonProperty("total_amount")
        private List<Double> totalAmount;

        public TransPortInfoResponse(){

        }

        public TransPortInfoResponse(Set<String> customerName, String email, List<ProductDto> products, List<Double> totalAmount) {
                this.customerName = customerName;
                this.email = email;
                this.products = products;
                this.totalAmount = totalAmount;
        }
}
