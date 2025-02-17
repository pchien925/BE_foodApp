package com.foodApp.service.impl;

import com.foodApp.dto.request.EmailRequest;
import com.foodApp.dto.request.RegisterRequest;
import com.foodApp.dto.request.SignInRequest;
import com.foodApp.dto.response.TokenResponse;
import com.foodApp.dto.response.UserResponse;
import com.foodApp.entity.Token;
import com.foodApp.entity.User;
import com.foodApp.exception.AccessDeniedException;
import com.foodApp.exception.InvalidDataException;
import com.foodApp.exception.ResourceNotFoundException;
import com.foodApp.mapper.UserMapper;
import com.foodApp.repository.TokenRepository;
import com.foodApp.repository.UserRepository;
import com.foodApp.service.AuthService;
import com.foodApp.service.EmailService;
import com.foodApp.service.JwtService;
import com.foodApp.service.TokenService;
import com.foodApp.util.TokenType;
import com.foodApp.util.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse authenticate(SignInRequest signInRequest) {
        //Authentication
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (AuthenticationException e){
            throw new AccessDeniedException(e.getMessage());
        }
        User user = userRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User or password not correct"));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);


        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .build();
    }

    @Override
    public UserResponse register(RegisterRequest request){
        if (userRepository.existsByUsername(request.getUsername())){
            throw new InvalidDataException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())){
            throw new InvalidDataException("Email already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.INACTIVE.name());
        String verificationCode = jwtService.generateVerificationToken(user);
        tokenService.save(Token.builder()
                        .username(user.getUsername())
                        .verificationToken(verificationCode)
                .build());
        EmailRequest emailRequest = EmailRequest.builder()
                .recipient(user.getEmail())
                .subject("Registration")
                .msgBody("click this link to activate your account: http://localhost:9990/api/v1/auth/activate?code=" + verificationCode)
                .build();
        emailService.sendSimpleMail(emailRequest);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public String activate(String token){
        String username = jwtService.extractUsername(token, TokenType.VERIFICATION_TOKEN);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Token verificationToken = tokenService.findByVerificationToken(token);
        if (jwtService.isValid(token, TokenType.VERIFICATION_TOKEN, user) && !verificationToken.getUsername().equals(username)){
            throw new InvalidDataException("Invalid verification code");
        }

        user.setStatus(UserStatus.ACTIVE.name());
        userRepository.save(user);

        return "Account activated";
    }

    @Override
    public String forgotPassword(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String resetToken = jwtService.generateResetToken(user);

        tokenService.save(Token.builder()
                .username(user.getUsername())
                .resetToken(resetToken)
                .build());

        EmailRequest emailRequest = EmailRequest.builder()
                .recipient(user.getEmail())
                .subject("Reset Password")
                .msgBody("click this link to reset your password: http://localhost:9990/api/v1/auth/reset-password?code=" + resetToken)
                .build();
        emailService.sendSimpleMail(emailRequest);

        return "Reset password link sent to your email";
    }
}
