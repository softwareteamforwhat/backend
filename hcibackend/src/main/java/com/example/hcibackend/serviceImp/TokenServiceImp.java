package com.example.hcibackend.serviceImp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.hcibackend.entity.User;
import com.example.hcibackend.service.TokenService;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImp implements TokenService {

    @Override
    public String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(String.valueOf(user.getUid()))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

    @Override
    public String getFirstToken(String uid, String password) {
        String token="";
        token = JWT.create().withAudience(String.valueOf(uid))
                .sign(Algorithm.HMAC256(password));
        return token;
    }


}
