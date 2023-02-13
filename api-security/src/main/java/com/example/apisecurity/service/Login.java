package com.example.apisecurity.service;

import lombok.Getter;

public class Login {

    @Getter
    private final Jwt accessToken;
    @Getter
    private final Jwt refreshToken;

    private static final Long ACCESS_VALIDITY=1440L;
    private static final Long REFRESH_VALIDITY=2880L;


    private Login(Jwt accessToken, Jwt refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static Login of(Long userId
            ,String accessSecret, String refreshSecret){
        return  new Login(
                Jwt.of(userId,ACCESS_VALIDITY,accessSecret),
                Jwt.of(userId,REFRESH_VALIDITY,refreshSecret)
        );
    }












}
