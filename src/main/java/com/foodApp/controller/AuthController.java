package com.foodApp.controller;

import com.foodApp.dto.request.*;
import com.foodApp.dto.response.ResponseData;
import com.foodApp.dto.response.TokenResponse;
import com.foodApp.dto.response.UserResponse;
import com.foodApp.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
@Slf4j(topic = "AUTH_CONTROLLER")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseData<TokenResponse> login(@RequestBody @Valid SignInRequest signInRequest) {
        return ResponseData.<TokenResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Login success")
                .data(authService.authenticate(signInRequest))
                .build();

    }

    @PostMapping("/register")
    public ResponseData<UserResponse> register(@RequestBody @Valid RegisterRequest request){
        return ResponseData.<UserResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Register success")
                .data(authService.register(request))
                .build();
    }

    @PostMapping("/verify")
    public ResponseData<String> verify(@RequestBody @Valid VerifyRequest request){
        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Verify success")
                .data(authService.verifyEmail(request))
                .build();
    }

    @PostMapping("/forgot-password")
    public ResponseData<String> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request){
        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Forgot password success")
                .data(authService.forgotPassword(request))
                .build();
    }

    @PostMapping("/reset-password")
    public ResponseData<String> resetPassword(@RequestBody @Valid ResetPasswordRequest request){
        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Reset password success")
                .data(authService.resetPassword(request))
                .build();
    }

    @PostMapping("/change-password")
    public ResponseData<String> changePassword(@RequestBody @Valid ChangePasswordRequest request){
        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Change password success")
                .data(authService.changePassword(request))
                .build();
    }
}
