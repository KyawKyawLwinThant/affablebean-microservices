package com.example.transportationservice.dao;

import com.example.transportationservice.ds.CustomerOrder;
import com.example.transportationservice.ds.TransPortInfoResponse;
import com.example.transportationservice.entity.CustomerOrderProduct;
import com.example.transportationservice.entity.Product;
import com.example.transportationservice.entity.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface CustomerOrderProductDao extends JpaRepository<CustomerOrderProduct,Integer> {

    @Query("""
    select new com.example.transportationservice.ds.TransPortInfoResponse(c.name,c.email,co.products,co.totalAmount) from Customer c
    inner join c.customerOrderProduct co inner join co.products p where c.email =:email
""")
    TransPortInfoResponse findTransPortInfoResponse(@Param("email") String email);

    @Query("""
    select new com.example.transportationservice.entity.ProductDto(co.orderId,p.name,p.price,p.quantity) 
    from Product p inner join p.customerOrderProduct co
    inner join co.customer c where c.email=:email
""")
    List<ProductDto> findProductsByCustomerEmail(@Param("email") String email);

    @Query("""
    select c.name from Customer c where c.email=:email
""")
    public Set<String> findCustomerNameByEmail(@Param("email") String email);

    @Query("""
    select new com.example.transportationservice.ds.CustomerOrder(co.totalAmount) 
    from CustomerOrderProduct co where co.customer.email = :email
""")
    public List<CustomerOrder> findTotalAmountByCustomerEmail(@Param("email") String email);
}
