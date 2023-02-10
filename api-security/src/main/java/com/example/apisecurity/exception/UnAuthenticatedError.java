package com.example.apisecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnAuthenticatedError extends ResponseStatusException {
    public UnAuthenticatedError(){
        super(HttpStatus.UNAUTHORIZED,"UnAuthenticated!");
    }
}
