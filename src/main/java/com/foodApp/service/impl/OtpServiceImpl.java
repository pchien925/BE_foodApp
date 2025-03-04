package com.foodApp.service.impl;

import com.foodApp.entity.Otp;
import com.foodApp.entity.User;
import com.foodApp.exception.ResourceNotFoundException;
import com.foodApp.repository.OtpRepository;
import com.foodApp.service.OtpService;
import com.foodApp.util.OtpType;
import com.foodApp.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final OtpRepository otpRepository;

    @Value("${otp.verification.expiryMinute}")
    private long VerificationTime;

    @Value("${otp.reset-password.expiryMinute}")
    private long ResetPasswordTime;

    @Value("${otp.update-phone.expiryMinute}")
    private long UpdatePhoneTime;

    @Value("${otp.update-email.expiryMinute}")
    private long UpdateEmailTime;

    @Override
    public Otp generateOtp(User user, String type) {
        Otp otp = new Otp();
        String code = OtpUtil.generateOtp();
        if (type.equals(OtpType.VERIFICATION.name())){
            otp.setExpiresAt(LocalDateTime.now().plusMinutes(VerificationTime));
        }else if (type.equals(OtpType.RESET_PASSWORD.name())){
            otp.setExpiresAt(LocalDateTime.now().plusMinutes(ResetPasswordTime));
        }
        else if (type.equals(OtpType.UPDATE_PHONE.name())){
            otp.setExpiresAt(LocalDateTime.now().plusMinutes(UpdatePhoneTime));
        }
        else if (type.equals(OtpType.UPDATE_EMAIL.name())){
            otp.setExpiresAt(LocalDateTime.now().plusMinutes(UpdateEmailTime));
        }
        otp.setUser(user);
        otp.setCode(code);
        otp.setType(type);
        return otp;
    }

    @Override
    public void save(Otp otp){
        otpRepository.save(otp);
    }

    @Override
    public Otp findByCodeAndTypeAndUser_Id(String code, String type, Long userId){
        return otpRepository.findByCodeAndTypeAndUser_Id(code, type, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Otp code is not correct"));
    }
}
