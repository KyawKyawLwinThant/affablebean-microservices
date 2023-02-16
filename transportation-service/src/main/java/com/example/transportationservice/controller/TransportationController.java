package com.example.transportationservice.controller;

import com.example.transportationservice.dao.ProductDao;
import com.example.transportationservice.ds.TransPortFindRequest;
import com.example.transportationservice.ds.TransPortInfoRequest;
import com.example.transportationservice.ds.TransPortInfoResponse;
import com.example.transportationservice.entity.Product;
import com.example.transportationservice.entity.ProductDto;
import com.example.transportationservice.service.TransportService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transport")
public class TransportationController {
    @Autowired
    private TransportService transportService;

    record TransPortInfoResponseSuccess(String name){}
    @PostMapping("/save-transport-info")
    public TransPortInfoResponseSuccess transPortInfo(@RequestBody
                                               TransPortInfoRequest request){
        transportService.saveTransPortService(request);
        return new TransPortInfoResponseSuccess("success");
    }
    //public ProductDto(String name, String description, LocalDateTime lastUpdate, double price, String categoryName, int quantity) {
    //email


    @PostMapping("/find-transport-info")
    public TransPortInfoResponse
    findTransPortInfoResponse(@RequestBody TransPortFindRequest request){
        return transportService
                .findTransPortInfo(request.email(),request.password());
    }


}
