package com.foodApp.service;

import com.foodApp.entity.Token;

public interface TokenService {
    Token findByEmail(String email);

    Token findByVerificationToken(String verificationToken);

    void save(Token token);
}
