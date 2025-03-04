package com.foodApp.service;

import com.foodApp.entity.Otp;
import com.foodApp.entity.User;

public interface OtpService {
    Otp generateOtp(User user, String type);

    void save(Otp otp);

    Otp findByCodeAndTypeAndUser_Id(String code, String type, Long userId);
}
