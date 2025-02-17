package com.foodApp.service;

import com.foodApp.dto.request.RegisterRequest;
import com.foodApp.dto.request.SignInRequest;
import com.foodApp.dto.response.TokenResponse;
import com.foodApp.dto.response.UserResponse;

public interface AuthService {
    TokenResponse authenticate(SignInRequest signInRequest);

    UserResponse register(RegisterRequest request);

    String activate(String token);

    String forgotPassword(String email);
}
