package vn.edu.hcmute.foodapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmute.foodapp.dto.request.*;
import vn.edu.hcmute.foodapp.dto.response.SendOtpResponse;
import vn.edu.hcmute.foodapp.dto.response.TokenResponse;
import vn.edu.hcmute.foodapp.dto.response.UserResponse;
import vn.edu.hcmute.foodapp.dto.response.VerifyOtpResponse;
import vn.edu.hcmute.foodapp.entity.Role;
import vn.edu.hcmute.foodapp.entity.User;
import vn.edu.hcmute.foodapp.entity.UserHasRole;
import vn.edu.hcmute.foodapp.exception.InvalidDataException;
import vn.edu.hcmute.foodapp.exception.ResourceNotFoundException;
import vn.edu.hcmute.foodapp.mapper.UserMapper;
import vn.edu.hcmute.foodapp.repository.RoleRepository;
import vn.edu.hcmute.foodapp.repository.UserRepository;
import vn.edu.hcmute.foodapp.service.AuthenticationService;
import vn.edu.hcmute.foodapp.service.JwtService;
import vn.edu.hcmute.foodapp.service.OtpService;
import vn.edu.hcmute.foodapp.service.RedisService;
import vn.edu.hcmute.foodapp.util.enumeration.EOtpType;
import vn.edu.hcmute.foodapp.util.enumeration.ERoleName;
import vn.edu.hcmute.foodapp.util.enumeration.ETokenType;
import vn.edu.hcmute.foodapp.util.enumeration.EUserStatus;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    private static final String AT_BLACKLIST_PREFIX = "bl_at:";
    private static final String RT_BLACKLIST_PREFIX = "bl_rt:";


    @Override
    public TokenResponse signIn(SignInRequest request) {
        log.info("Get access token");

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            log.info("isAuthenticated = {}", authenticate.isAuthenticated());
            log.info("Authorities: {}", authenticate.getAuthorities().toString());

            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (BadCredentialsException | DisabledException e) {
            log.error("errorMessage: {}", e.getMessage());
            throw new AccessDeniedException(e.getMessage());
        }

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new AccessDeniedException("User not found"));

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .build();
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        log.info("Get new access token");

        String email = jwtService.extractUsername(request.getRefreshToken(), ETokenType.REFRESH_TOKEN);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AccessDeniedException("User not found"));

        String storedRefreshToken = (String) redisService.get("refresh:" + user.getEmail());
        if (storedRefreshToken == null || !storedRefreshToken.equals(request.getRefreshToken())) {
            throw new AccessDeniedException("Invalid or revoked refresh token");
        }

        if (!jwtService.isValid(request.getRefreshToken(), ETokenType.REFRESH_TOKEN, user)) {
            throw new AccessDeniedException("Invalid refresh token");
        }

        String newAccessToken = jwtService.generateAccessToken(user);

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .userId(user.getId())
                .build();
    }

    @Override
    @Transactional
    public UserResponse signUp(SignUpRequest request){
        log.info("Sign up user");
        if (!request.getPassword().equals(request.getConfirmPassword())){
            throw new InvalidDataException("Password and confirm password do not match");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AccessDeniedException("Email already exists");
        }

        User user = UserMapper.INSTANCE.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepository.findByName(ERoleName.ROLE_USER)
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        user.getRoles().add(UserHasRole.builder()
                        .role(role)
                        .user(user)
                .build());

        User savedUser = userRepository.save(user);
        otpService.generateAndSendOtp(savedUser.getEmail(), EOtpType.REGISTER);

        return UserMapper.INSTANCE.toResponse(savedUser);
    }

    @Override
    public void verifyEmail(VerifyOtpRequest request) {
        log.info("Verify email");
        if (!otpService.verifyOtp(request, EOtpType.REGISTER)) {
            throw new InvalidDataException("Invalid OTP");
        }

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setStatus(EUserStatus.ACTIVE);
        userRepository.save(user);
    }

    @Override
    public SendOtpResponse sendOtpForForgotPassword(SendOtpRequest request) {
        String email = request.getEmail();
        if (email == null) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Phone number not registered"));

        if (user.getStatus() != EUserStatus.ACTIVE) {
            throw new AccessDeniedException("Account is not active");
        }

        return otpService.generateAndSendOtp(email, EOtpType.FORGOT_PASSWORD);
    }

    @Override
    public VerifyOtpResponse verifyOtpForForgotPassword(VerifyOtpRequest request) {
        String email = request.getEmail();
        if (email == null) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Phone number not registered"));
        if (otpService.verifyOtp(request, EOtpType.FORGOT_PASSWORD)) {
            String verificationToken = jwtService.generateResetToken(user);
            return VerifyOtpResponse.builder()
                    .email(request.getEmail())
                    .verificationToken(verificationToken)
                    .build();
        }
        throw new AccessDeniedException("Invalid or expired OTP");
    }

    @Transactional
    @Override
    public void resetForgotPassword(String verificationToken, SetPasswordRequest request) {
        String email;
        try {
            email = jwtService.extractUsername(verificationToken, ETokenType.RESET_TOKEN);
        } catch (Exception e) {
            throw new AccessDeniedException("Invalid verification token");
        }

        if (jwtService.isTokenExpired(verificationToken, ETokenType.RESET_TOKEN)) {
            throw new AccessDeniedException("Expired verification token");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (request.getPassword().length() < 8 || !request.getPassword().matches(".*[A-Za-z].*") || !request.getPassword().matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain letters and numbers");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Phone number not registered"));

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }
}
