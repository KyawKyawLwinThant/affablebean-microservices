package com.example.apisecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidLinkError extends ResponseStatusException {

    public InvalidLinkError(){
        super(HttpStatus.UNAUTHORIZED,"Invalid Link Error!");
    }
}
