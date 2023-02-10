package com.example.apisecurity.controller;

import com.example.apisecurity.data.User;
import com.example.apisecurity.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/security")
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/hello")
    public String hello() {
        return "Hello!";
    }

    record RegisterRequest(@JsonProperty("first_name") String firstName,
                           @JsonProperty("last_name") String lastName,
                           String email,
                           String password,
                           @JsonProperty("confirm_password") String confirmPassword) {

    }

    record RegisterResponse(
            Long id,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            String email) {

    }
    record LoginRequest(String email,String password){

    }
    record LoginResponse(String token){}


    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest,
                               HttpServletResponse response){
        var login=userService
                .login(loginRequest.email,loginRequest.password);
        Cookie cookie=new Cookie("refresh_token"
                ,login.getRefreshToken().getToken());
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setPath("/security");
        response.addCookie(cookie);

        return new LoginResponse(login.getAccessToken().getToken());
    }


  /*
  curl -X POST -H "Content-Type: application/json" -d '{"first_name":"john","last_name":"william","email":"william","password":"john","confirm_password":"john"}' http://localhost:8070/security/register
   */



    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request){
        User user=userService.register(
                request.firstName,
                request.lastName,
                request.email,
                request.password,
                request.confirmPassword
        );

        return new RegisterResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    record UserResponse(Long id,
                        @JsonProperty("first_name") String firstName,
                        @JsonProperty("last_name") String lastName,
                        String email){}

   @GetMapping("/user")
    public UserResponse user(HttpServletRequest request){
        var user=(User) request.getAttribute("user");
        return new UserResponse(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail());
    }
    record RefreshResponse(String token){}
    @PostMapping("/refresh")
    public RefreshResponse refresh(@CookieValue("refresh_token")
                                    String refreshToken){

        return new RefreshResponse(
                userService.refreshAccess(refreshToken)
                        .getAccessToken().getToken()
        );

    }
    record LogoutResponse(String message){}
    @PostMapping("/logout")
    public LogoutResponse logout(@CookieValue("refresh_token")
                                 String refreshToken,
                                 HttpServletResponse response){
        Cookie cookie=new Cookie("refresh_token",null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return new LogoutResponse("successfully logout!");
    }




}
