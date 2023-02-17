package com.example.transportationservice.service;

import com.example.transportationservice.exception.AuthorizationError;
import com.example.transportationservice.dao.CustomerOrderProductDao;
import com.example.transportationservice.ds.CustomerOrder;
import com.example.transportationservice.ds.TransPortFindRequest;
import com.example.transportationservice.ds.TransPortInfoRequest;
import com.example.transportationservice.ds.TransPortInfoResponse;
import com.example.transportationservice.entity.Customer;
import com.example.transportationservice.entity.CustomerOrderProduct;
import com.example.transportationservice.entity.Product;
import com.example.transportationservice.entity.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransportService {

    private RestTemplate template=new RestTemplate();

    @Autowired
    private CustomerOrderProductDao orderProductDao;
    @Transactional
    public void saveTransPortService(TransPortInfoRequest request){
        System.out.println("email"+ request.email());
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

    record Token(String token){}
    public TransPortInfoResponse findTransPortInfo(String email,
                                                   String password) {
        try{
            var request=new TransPortFindRequest(email,password);
            ResponseEntity<Token> response =template
                    .postForEntity("http://localhost:8080/security/login",
                            request,Token.class);
            String token;
            if(response.getStatusCode().is2xxSuccessful()){
                token=response.getBody().token();

                HttpHeaders headers=new HttpHeaders();
                headers.add("Authorization"
                        ,"Bearer "+ token);
                HttpEntity<String> httpEntity=new HttpEntity<>(headers);

                ResponseEntity<String> response1=template
                        .exchange("http://localhost:8080/security/user",
                                HttpMethod.GET,
                                httpEntity,
                                String.class);

                if(response1.getStatusCode().is2xxSuccessful()){
                    List<ProductDto> products=orderProductDao
                            .findProductsByCustomerEmail(email);


                    Set<String> name=orderProductDao
                            .findCustomerNameByEmail(email);

                    List<CustomerOrder> customerOrders= orderProductDao.findTotalAmountByCustomerEmail(email);
                    return new TransPortInfoResponse(
                            name,
                            email,
                            products,
                            customerOrders
                    );
                }else {
                    throw new AuthorizationError();
                }
            }else{
                throw new AuthorizationError();
            }
        }catch (Exception e){
            throw new AuthorizationError();
        }

    }


}
