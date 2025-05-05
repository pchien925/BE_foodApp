package vn.edu.hcmute.foodapp.service;

import jakarta.transaction.Transactional;
import vn.edu.hcmute.foodapp.dto.request.*;
import vn.edu.hcmute.foodapp.dto.response.SendOtpResponse;
import vn.edu.hcmute.foodapp.dto.response.TokenResponse;
import vn.edu.hcmute.foodapp.dto.response.UserResponse;
import vn.edu.hcmute.foodapp.dto.response.VerifyOtpResponse;

public interface AuthenticationService {
    TokenResponse signIn(SignInRequest request);

    TokenResponse refreshToken(RefreshTokenRequest refreshToken);

    UserResponse signUp(SignUpRequest request);

    void verifyEmail(VerifyOtpRequest request);

    SendOtpResponse sendOtpForForgotPassword(SendOtpRequest request);

    VerifyOtpResponse verifyOtpForForgotPassword(VerifyOtpRequest request);

    void resetForgotPassword(String verificationToken, SetPasswordRequest request);
}
