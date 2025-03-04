package com.foodApp.service.impl;

import com.foodApp.dto.request.*;
import com.foodApp.dto.response.TokenResponse;
import com.foodApp.dto.response.UserResponse;
import com.foodApp.entity.Otp;
import com.foodApp.entity.User;
import com.foodApp.exception.InvalidDataException;
import com.foodApp.exception.ResourceNotFoundException;
import com.foodApp.mapper.UserMapper;
import com.foodApp.repository.UserRepository;
import com.foodApp.service.*;
import com.foodApp.util.OtpType;
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
import java.time.LocalDateTime;
import java.util.Optional;

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
    private final OtpService otpService;

    @Override
    public TokenResponse authenticate(SignInRequest signInRequest) {
        //Authentication
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (AuthenticationException e){
            throw new com.foodApp.exception.AuthenticationException("Email or password not correct");
        }
        User user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getStatus().equals(UserStatus.INACTIVE.name())){
            throw new InvalidDataException("Account not activated");
        }

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
        if (userRepository.existsByEmail(request.getEmail())){
            throw new InvalidDataException("Email already exists");
        }
        if (userRepository.existsByPhone(request.getPhone())){
            throw new InvalidDataException("Phone number already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User saveUser = userRepository.save(user);
        Otp otp = otpService.generateOtp(user, "VERIFICATION");
        otpService.save(otp);
        EmailRequest emailRequest = EmailRequest.builder()
                .recipient(user.getEmail())
                .subject("Registration")
                .msgBody("Your verification code is: " + otp.getCode() + ". Please don't share this code with anyone")
                .build();
        emailService.sendSimpleMail(emailRequest);
        return userMapper.toResponse(saveUser);
    }

    @Override
    public String verifyEmail(VerifyRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Otp otp = otpService.findByCodeAndTypeAndUser_Id(request.getCode(), OtpType.VERIFICATION.name(), user.getId());

        if (!otp.isUsed() && otp.getExpiresAt().isAfter(LocalDateTime.now())) {
            user.setStatus(UserStatus.ACTIVE.name());
            userRepository.save(user);
            otp.setUsed(true);
            otpService.save(otp);
            return "Email verified";
        }
        throw new InvalidDataException("Invalid verification code");
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        User user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Otp otp = otpService.generateOtp(user, OtpType.RESET_PASSWORD.name());
        otp.setData(request.getEmail());
        otpService.save(otp);
        EmailRequest emailRequest = EmailRequest.builder()
                .recipient(user.getEmail())
                .subject("Reset Password")
                .msgBody("Your reset password code is: " + otp.getCode() + ". Please don't share this code with anyone")
                .build();
        emailService.sendSimpleMail(emailRequest);
        return "Reset password code sent to your email";
    }

    @Override
    public String resetPassword(ResetPasswordRequest request) {
        User user  = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Otp otp = otpService.findByCodeAndTypeAndUser_Id(request.getOtpCode(), OtpType.RESET_PASSWORD.name(), user.getId());
        if (!otp.isUsed() && otp.getExpiresAt().isAfter(LocalDateTime.now())) {
            otp.setUsed(true);
            otpService.save(otp);
            return "Reset";
        }
        throw new InvalidDataException("Invalid verification code");
    }

    @Override
    public String changePassword(ChangePasswordRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!request.getPassword().equals(request.getConfirmPassword())){
            throw new InvalidDataException("Password not match");
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return "Password changed";
    }

}
