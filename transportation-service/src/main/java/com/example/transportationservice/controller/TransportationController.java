package com.example.transportationservice.controller;

import com.example.transportationservice.dao.ProductDao;
import com.example.transportationservice.ds.TransPortInfoRequest;
import com.example.transportationservice.entity.ProductDto;
import com.example.transportationservice.service.TransportService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/transport")
public class TransportationController {
    @Autowired
    private TransportService transportService;

    record TransPortInfoResponse(String name){}
    @PostMapping("/save-transport-info")
    public TransPortInfoResponse transPortInfo(@RequestBody
                                               TransPortInfoRequest request){
        transportService.saveTransPortService(request);
        return new TransPortInfoResponse("success");
    }
}
