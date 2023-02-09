package com.example.apisecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoBearToken extends ResponseStatusException {
    public NoBearToken(){
        super(HttpStatus.BAD_REQUEST,"No Bearer Token!");
    }
}
