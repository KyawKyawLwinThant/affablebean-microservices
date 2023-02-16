package com.example.affabblebeanui.ds;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public  class TransPortInfoEntity {

        @JsonProperty("customer_name") private List<String> customerName;
        private String email;
        private List<ProductDto> products;
        private List<CustomerOrder> customerOrders=new ArrayList<>();

        public TransPortInfoEntity(){

        }

        public TransPortInfoEntity(List<String> customerName, String email, List<ProductDto> products,
                                   List<CustomerOrder> customerOrders) {
                this.customerName = customerName;
                this.email = email;
                this.products = products;
                this.customerOrders=customerOrders;
        }
}
