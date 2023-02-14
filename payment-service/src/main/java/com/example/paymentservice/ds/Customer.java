package com.example.paymentservice.ds;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/*
record RegisterRequest(@JsonProperty("first_name") String firstName,
                           @JsonProperty("last_name") String lastName,
                           String email,
                           String password,
                           @JsonProperty("confirm_password") String confirmPassword) {
 */
@Data
public class Customer {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    private String password;
    @JsonProperty("confirm_password")
    private String confirmPassword;
}
