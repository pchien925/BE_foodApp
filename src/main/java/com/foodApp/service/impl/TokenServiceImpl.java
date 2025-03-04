package com.foodApp.service.impl;

import com.foodApp.entity.Token;
import com.foodApp.exception.ResourceNotFoundException;
import com.foodApp.repository.TokenRepository;
import com.foodApp.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    public Token findByEmail(String email){
        return tokenRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
    }

    @Override
    public Token findByVerificationToken(String verificationToken){
        return tokenRepository.findByVerificationToken(verificationToken)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
    }

    @Override
    public void save(Token token){
        Optional<Token> optional = tokenRepository.findByEmail(token.getEmail());
        if (optional.isEmpty())
            tokenRepository.save(token);
        else {
            Token t = optional.get();
            t.setAccessToken(token.getAccessToken());
            t.setRefreshToken(token.getRefreshToken());
            if (token.getVerificationToken() != null) {
                t.setVerificationToken(token.getVerificationToken());
            }
            if (token.getResetToken() != null) {
                t.setResetToken(token.getResetToken());
            }
            tokenRepository.save(t);
        }
    }
}
