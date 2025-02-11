package com.foodApp.service.impl;

import com.foodApp.dto.request.EmailRequest;
import com.foodApp.dto.request.RegisterRequest;
import com.foodApp.dto.request.SignInRequest;
import com.foodApp.dto.response.TokenResponse;
import com.foodApp.dto.response.UserResponse;
import com.foodApp.entity.User;
import com.foodApp.exception.ResourceNotFoundException;
import com.foodApp.mapper.UserMapper;
import com.foodApp.repository.UserRepository;
import com.foodApp.service.AuthService;
import com.foodApp.service.EmailService;
import com.foodApp.service.JwtService;
import com.foodApp.util.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse authenticate(SignInRequest signInRequest) {
        //Authentication
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));

        User user = userRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User or password not correct"));

        String accessToken = jwtService.generateToken(user);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken("refreshToken")
                .userId(1L)
                .build();
    }

    @Override
    public UserResponse register(RegisterRequest request){
        if (userRepository.existsByUsername(request.getUsername())){
            throw new ResourceNotFoundException("Username already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.INACTIVE.name());
        int otpCode = generateVerificationCode();
        user.setVerificationCode(otpCode);
        EmailRequest emailRequest = EmailRequest.builder()
                .recipient(user.getEmail())
                .subject("Registration")
                .msgBody("Your verification code is " + otpCode + ". Use this code to verify your account.")
                .build();
        emailService.sendSimpleMail(emailRequest);
        return userMapper.toResponse(userRepository.save(user));
    }

    private Integer generateVerificationCode(){
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
}
