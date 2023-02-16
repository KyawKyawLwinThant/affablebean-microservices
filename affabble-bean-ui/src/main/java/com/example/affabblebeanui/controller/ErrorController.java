package com.example.affabblebeanui.controller;

import com.example.affabblebeanui.exception.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Controller
public class ErrorController {
    @ExceptionHandler({IllegalArgumentException.class,
    AuthenticationException.class})
    public String handleException(Throwable throwable, Model model)throws Throwable{
        if(throwable instanceof IllegalArgumentException){
            model.addAttribute("message",throwable.getMessage());
            model.addAttribute("statusCode","404 Not Found!");
        }
        if(throwable instanceof AuthenticationException){
            model.addAttribute("message",throwable.getMessage());
            model.addAttribute("statusCode","415 Unauthenticated Error!");
        }

        return "error";
    }
}
