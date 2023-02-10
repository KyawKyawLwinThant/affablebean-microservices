package com.example.apisecurity.service;

import com.example.apisecurity.data.User;
import com.example.apisecurity.data.UserDao;
import com.example.apisecurity.exception.InvalidCredentialError;
import com.example.apisecurity.exception.PasswordNotMatchError;
import com.example.apisecurity.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${secret.access-token.key}")
    private String accessSecret;
    @Value("${secret.refresh-token.key}")
    private String refreshSecret;

    public Login refreshAccess(String refreshToken){
        var refreshJwt=Jwt.from(refreshToken,refreshSecret);

        return  Login.of(refreshJwt.getUserId(),accessSecret,
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
        return Login.of(user.getId(), accessSecret,refreshSecret);
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
}
