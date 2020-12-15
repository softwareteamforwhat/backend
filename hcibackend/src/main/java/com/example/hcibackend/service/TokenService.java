package com.example.hcibackend.service;

import com.example.hcibackend.entity.User;

public interface TokenService {
    String getToken(User user);
}
