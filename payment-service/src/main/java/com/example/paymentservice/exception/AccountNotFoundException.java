package com.example.paymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccountNotFoundException extends ResponseStatusException {
    public AccountNotFoundException(){
        super(HttpStatus.NOT_FOUND,"Account not Found");
    }
}
