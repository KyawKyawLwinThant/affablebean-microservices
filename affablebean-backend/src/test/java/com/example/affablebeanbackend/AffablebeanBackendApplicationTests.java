package com.example.affablebeanbackend;

import com.example.affablebeanbackend.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AffablebeanBackendApplicationTests {
    @Autowired
    private ProductService productService;

    @Test
    void contextLoads() {
       LocalDateTime localDateTime= productService
               .convertToLocalDateTime("2023-01-29 12:38:05");
        Assertions.assertEquals(LocalDateTime.of(2023,1,
                        29
                        ,12,38,5)
                ,localDateTime);
    }

}
