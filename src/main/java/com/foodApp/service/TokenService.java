package com.foodApp.service;

import com.foodApp.entity.Token;

public interface TokenService {
    Token findByUsername(String username);

    Token findByVerificationToken(String verificationToken);

    void save(Token token);
}
