package com.example.transportationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthorizationError extends ResponseStatusException {

    public AuthorizationError(){
        super(HttpStatus.FORBIDDEN,"Authorization Error!");
    }
}
