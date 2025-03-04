package com.foodApp.service.impl;

import com.foodApp.dto.request.EmailRequest;
import com.foodApp.dto.request.EmailUpdateRequest;
import com.foodApp.dto.request.PhoneUpdateRequest;
import com.foodApp.dto.request.UserRequestDTO;
import com.foodApp.dto.response.UserResponse;
import com.foodApp.entity.Otp;
import com.foodApp.entity.User;
import com.foodApp.exception.DuplicatedException;
import com.foodApp.exception.InvalidDataException;
import com.foodApp.exception.ResourceNotFoundException;
import com.foodApp.mapper.UserMapper;
import com.foodApp.repository.UserRepository;
import com.foodApp.service.CloudinaryService;
import com.foodApp.service.EmailService;
import com.foodApp.service.OtpService;
import com.foodApp.service.UserService;
import com.foodApp.util.OtpType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OtpService otpService;
    private final EmailService emailService;
    private final CloudinaryService cloudinaryService;

    @Override
    public Long create(UserRequestDTO request){
        if (userRepository.existsByEmail(request.getEmail())){
            throw new DuplicatedException("Username already exists");
        }
        if (userRepository.existsByPhone(request.getPhone())){
            throw new DuplicatedException("Phone number already exists");
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setGender(request.getGender());
        user.setFullName(request.getFullName());
        user.setStatus(request.getStatus());
        user.setDob(request.getDob());
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public void updateStatus(long userId, String status){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setStatus(status);
        userRepository.save(user);
    }

    @Override
    public UserResponse findById(long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse update(Long userId, UserRequestDTO request){
        if (userRepository.existsByEmail(request.getEmail())){
            throw new DuplicatedException("Email already exists");
        }
        if (userRepository.existsByPhone(request.getPhone())){
            throw new DuplicatedException("Phone number already exists");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(Long userId, UserRequestDTO request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userMapper.update(request, user);
        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public String updateEmail(Long userId, EmailUpdateRequest request){
        if ( userRepository.existsByEmail(request.getNewEmail())){
            throw new DuplicatedException("Email already exists");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Otp otp = otpService.generateOtp(user, OtpType.UPDATE_EMAIL.name());
        otp.setData(request.getNewEmail());
        otpService.save(otp);
        EmailRequest emailRequest = EmailRequest.builder()
                .recipient(request.getNewEmail())
                .subject("update verification")
                .msgBody("Your verification code is: " + otp.getCode() + ". Please don't share this code with anyone")
                .build();
        emailService.sendSimpleMail(emailRequest);
        return "Verification code sent to your email";
    }

    @Override
    public String verifyUpdateEmail(Long userId, String otpCode) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(("User not existed")));

        Otp otp = otpService.findByCodeAndTypeAndUser_Id(otpCode, OtpType.UPDATE_EMAIL.name(), userId);


        if (!otp.isUsed() && otp.getExpiresAt().isAfter(LocalDateTime.now())) {
            if (otp.getData().isEmpty())
                throw new InvalidDataException("Invalid or expired OTP");
            user.setEmail(otp.getData());
            userRepository.save(user);
            otp.setUsed(true);
            otpService.save(otp);
            return "update email success";
        }
        throw new InvalidDataException("Invalid or expired OTP");
    }

    @Override
    public String updatePhone(Long userId, PhoneUpdateRequest request){
        if ( userRepository.existsByPhone(request.getPhone())){
            throw new DuplicatedException("Phone already exists");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Otp otp = otpService.generateOtp(user, OtpType.UPDATE_PHONE.name());
        otpService.save(otp);
        EmailRequest emailRequest = EmailRequest.builder()
                .recipient(user.getEmail())
                .subject("update verification")
                .msgBody("Your verification code is: " + otp.getCode() + ". Please don't share this code with anyone")
                .build();
        emailService.sendSimpleMail(emailRequest);
        return "Verification code sent to your email";
    }

    @Override
    public String verifyUpdatePhone(Long userId, String otpCode) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(("User not existed")));

        Otp otp = otpService.findByCodeAndTypeAndUser_Id(otpCode, OtpType.UPDATE_PHONE.name(), userId);


        if (!otp.isUsed() && otp.getExpiresAt().isAfter(LocalDateTime.now())) {
            if (otp.getData().isEmpty())
                throw new InvalidDataException("Invalid or expired OTP");
            user.setPhone(otp.getData());
            userRepository.save(user);
            otp.setUsed(true);
            otpService.save(otp);
            return "update phone success";
        }
        throw new InvalidDataException("Invalid or expired OTP");
    }

    @Override
    public String updateAvatar(Long userId, MultipartFile file){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String avatarUrl = "";
        try {
            avatarUrl = cloudinaryService.uploadFile(file);
        }
        catch (Exception e){
            throw new InvalidDataException("Invalid file");
        }

        user.setAvatar(avatarUrl);
        userRepository.save(user);
        return "Avatar updated";
    }
}
