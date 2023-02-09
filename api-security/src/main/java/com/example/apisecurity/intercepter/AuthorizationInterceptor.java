package com.example.apisecurity.intercepter;

import com.example.apisecurity.exception.NoBearToken;
import com.example.apisecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String handlerString=request.getHeader("Authorization");
        if(handlerString == null ||
        !handlerString.startsWith("Bearer ")){
            throw new NoBearToken();
        }
        request.setAttribute("user",
                userService.getUserFromToken(handlerString
                        .substring(7)));
        return true;
    }
}
