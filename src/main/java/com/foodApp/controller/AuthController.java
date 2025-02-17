package com.foodApp.controller;

import com.foodApp.dto.request.RegisterRequest;
import com.foodApp.dto.request.SignInRequest;
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

    @GetMapping("/activate")
    public ResponseData<String> activate(@RequestParam String token){
        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Account activated")
                .data(authService.activate(token))
                .build();
    }


}
