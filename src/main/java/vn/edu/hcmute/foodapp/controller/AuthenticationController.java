package vn.edu.hcmute.foodapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmute.foodapp.dto.request.*;
import vn.edu.hcmute.foodapp.dto.response.*;
import vn.edu.hcmute.foodapp.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
@Slf4j(topic = "AUTH_CONTROLLER")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    @Operation(summary = "Sign in with phone and password", description = "Authenticate user and return access and refresh tokens")
    public ResponseData<TokenResponse> signIn(@RequestBody @Valid SignInRequest request) {
        log.info("Sign in request for phone: {}", request.getEmail());
        return ResponseData.<TokenResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Sign in successful")
                .data(authenticationService.signIn(request))
                .build();
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh access token", description = "Generate a new access token using refresh token")
    public ResponseData<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
        log.info("Refresh token request received");
        return ResponseData.<TokenResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Token refreshed successfully")
                .data(authenticationService.refreshToken(refreshToken))
                .build();
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Sign up", description = "Register a new user")
    public ResponseData<UserResponse> signUp(@RequestBody @Valid SignUpRequest request) {
        log.info("Sign up request for phone: {}", request.getPhone());
        return ResponseData.<UserResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Sign up successful")
                .data(authenticationService.signUp(request))
                .build();
    }

    @PostMapping("/verify-email")
    @Operation(summary = "Verify email", description = "Verify the email address of the user")
    public ResponseData<String> verifyEmail(@RequestBody VerifyOtpRequest request) {
        log.info("Verify email request for email: {}", request);
        authenticationService.verifyEmail(request);
        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Email verification successful")
                .data("Email has been verified successfully")
                .build();
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Send OTP for forgot password", description = "Send OTP to the user's email for password reset")
    public ResponseData<SendOtpResponse> sendOtpForForgotPassword(@RequestBody SendOtpRequest request) {
        log.info("Send OTP for forgot password request for email: {}", request.getEmail());
        return ResponseData.<SendOtpResponse>builder()
                .status(HttpStatus.OK.value())
                .message("OTP sent successfully")
                .data(authenticationService.sendOtpForForgotPassword(request))
                .build();
    }

    @PostMapping("/verify-otp")
    @Operation(summary = "Verify OTP for forgot password", description = "Verify the OTP sent to the user's email")
    public ResponseData<VerifyOtpResponse> verifyOtpForForgotPassword(@RequestBody VerifyOtpRequest request) {
        log.info("Verify OTP for forgot password request for email: {}", request.getEmail());
        return ResponseData.<VerifyOtpResponse>builder()
                .status(HttpStatus.OK.value())
                .message("OTP verification successful")
                .data(authenticationService.verifyOtpForForgotPassword(request))
                .build();
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password", description = "Reset the user's password using the verification token")
    public ResponseData<String> resetForgotPassword(
            @RequestHeader("X-Verification-Token") String verificationToken,
            @RequestBody SetPasswordRequest request) {
        log.info("Reset password request with verification token: {}", verificationToken);
        authenticationService.resetForgotPassword(verificationToken, request);
        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .message("Password reset successful")
                .data("Password has been reset successfully")
                .build();
    }
}