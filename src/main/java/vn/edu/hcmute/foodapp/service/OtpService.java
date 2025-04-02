package vn.edu.hcmute.foodapp.service;

import vn.edu.hcmute.foodapp.dto.request.VerifyOtpRequest;
import vn.edu.hcmute.foodapp.dto.response.SendOtpResponse;
import vn.edu.hcmute.foodapp.util.enumeration.EOtpType;

public interface OtpService {
    SendOtpResponse generateAndSendOtp(String email, EOtpType otpType);

    boolean verifyOtp(VerifyOtpRequest request, EOtpType otpType);
}
