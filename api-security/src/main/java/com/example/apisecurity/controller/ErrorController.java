package com.example.apisecurity.controller;


import com.example.apisecurity.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ErrorController {
    @ExceptionHandler({PasswordNotMatchError.class,
            InvalidCredentialError.class,
            NoBearToken.class,
    UserNotFoundException.class,
            UnAuthenticatedError.class})
    public ResponseEntity handleException(Throwable throwable)throws Throwable{
        if(throwable instanceof PasswordNotMatchError){
            return ResponseEntity
                    .badRequest()
                    .body("Password did not match!");
        }
        else if (throwable instanceof InvalidCredentialError){
            return ResponseEntity
                    .status(401)
                    .body("Invalid Credentials!");
        }
        else if(throwable instanceof NoBearToken){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No Bearer Token!");
        }
        else if(throwable instanceof UserNotFoundException){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User Not Found From Token!");
        }
        else if(throwable instanceof UnAuthenticatedError){
            return ResponseEntity
                    .status(401)
                    .body("Refresh Token is not authenticated");
        }

        return null;
    }
}
