package com.foodApp.service;

import com.foodApp.dto.request.EmailUpdateRequest;
import com.foodApp.dto.request.PhoneUpdateRequest;
import com.foodApp.dto.request.UserRequestDTO;
import com.foodApp.dto.request.VerifyRequest;
import com.foodApp.dto.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    Long create(UserRequestDTO request);

    void updateStatus(long userId, String status);

    UserResponse findById(long userId);

    UserResponse update(Long userId, UserRequestDTO request);

    UserResponse updateUser(Long userId, UserRequestDTO request);

    String updateEmail(Long userId, EmailUpdateRequest request);

    String verifyUpdateEmail(Long userId, String otpCode);

    String updatePhone(Long userId, PhoneUpdateRequest request);

    String verifyUpdatePhone(Long userId, String otpCode);

    String updateAvatar(Long userId, MultipartFile file);
}
