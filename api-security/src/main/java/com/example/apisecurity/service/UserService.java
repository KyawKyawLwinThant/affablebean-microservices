package com.example.apisecurity.service;

import com.example.apisecurity.data.PasswordRecovery;
import com.example.apisecurity.data.Token;
import com.example.apisecurity.data.User;
import com.example.apisecurity.data.UserDao;
import com.example.apisecurity.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailService mailService;

    @Value("${secret.access-token.key}")
    private String accessSecret;
    @Value("${secret.refresh-token.key}")
    private String refreshSecret;

    public void forgot(String email,String originUrl){
        var token= UUID.randomUUID().toString().replace("-","");
        var user=userDao.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        user.addPasswordRecovery(new PasswordRecovery(token));
        mailService.sendForgotMessage(email,token,originUrl);
        userDao.save(user);

    }


    public Boolean logout(String refreshToken){
        var refreshJwt=Jwt.from(refreshToken,refreshSecret);
        var user=userDao.findById(refreshJwt.getUserId())
                .orElseThrow(UserNotFoundException::new);
        var tokenIsRemoved=user.removeTokenIf(token -> Objects.equals(
                token.refreshToken(),refreshToken
        ));
        System.out.println("RemoveToken======================="
                +tokenIsRemoved);

        if(tokenIsRemoved){
            userDao.save(user);
        }
        return tokenIsRemoved;
    }

    public Login refreshAccess(String refreshToken){
        var refreshJwt=Jwt.from(refreshToken,refreshSecret);
        var user=userDao
                .findUserIdAndTokenByRefreshToken(refreshJwt.getUserId(),
                        refreshJwt.getToken(),
                        refreshJwt.getExpiredAt())
                .orElseThrow(UnAuthenticatedError::new);

        return  Login.of(user.getId(),accessSecret,
                refreshSecret);
    }

    public User getUserFromToken(String token){
        return userDao.findById(Jwt.from(token,accessSecret).getUserId())
                .orElseThrow(UserNotFoundException::new);
    }

    public Login login(String email,String password){
        var user=userDao.findUserByEmail(email)
                .orElseThrow(InvalidCredentialError::new);

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new InvalidCredentialError();
        }
         var login=Login.of(user.getId(),
                 accessSecret,refreshSecret);
        var refreshToken=login.getRefreshToken();
         user.addToken(new Token(
                 refreshToken.getToken(),
                 refreshToken.getIssuedAt(),
                 refreshToken.getExpiredAt()
         ));
         userDao.save(user);
        return login;
    }
    public User register(String firstName,
                         String lastName,
                         String email,
                         String password,
                         String confirmPassword){
        if(!Objects.equals(password,confirmPassword)){
            throw new PasswordNotMatchError();
        }

        return userDao.save(
                User.of(
                        firstName,
                        lastName,
                        email,
                        passwordEncoder.encode(password)
                )
        );

    }

    public void reset(String token, String password,
                      String passwordConfirm) {
        if(!Objects.equals(password,passwordConfirm)){
            throw new PasswordNotMatchError();
        }
        var user=userDao.findUserByPasswordRecoveryToken(token)
                .orElseThrow(InvalidLinkError::new);
        user.setPassword(passwordEncoder.encode(password));
        user.removePasswordRecoveryIf(p -> Objects.equals(p.token()
                ,token));
        userDao.save(user);

    }
}
