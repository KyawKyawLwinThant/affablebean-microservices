package com.example.paymentservice.controller;


import com.example.paymentservice.exception.InsufficientAmountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ErrorController {

    @ExceptionHandler({InsufficientAmountException.class})
    public ResponseEntity handleException(Throwable throwable)
            throws Throwable{
        if(throwable instanceof InsufficientAmountException){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Insufficient Amount!");

        }
        return null;
    }
}
