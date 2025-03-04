package com.foodApp.service;

import com.foodApp.dto.request.*;
import com.foodApp.dto.response.TokenResponse;
import com.foodApp.dto.response.UserResponse;
import jakarta.validation.Valid;

public interface AuthService {
    TokenResponse authenticate(SignInRequest signInRequest);

    UserResponse register(RegisterRequest request);

    String verifyEmail(VerifyRequest request);

    String forgotPassword(ForgotPasswordRequest request);

    String resetPassword( ResetPasswordRequest request);

    String changePassword(ChangePasswordRequest request);
}
