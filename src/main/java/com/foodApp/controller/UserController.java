package com.foodApp.controller;

import com.foodApp.dto.request.EmailUpdateRequest;
import com.foodApp.dto.request.PhoneUpdateRequest;
import com.foodApp.dto.request.UserRequestDTO;
import com.foodApp.dto.request.VerifyRequest;
import com.foodApp.dto.response.ResponseData;
import com.foodApp.dto.response.UserResponse;
import com.foodApp.service.CloudinaryService;
import com.foodApp.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final CloudinaryService cloudinaryService;

    @GetMapping("/{userId}")
    public ResponseData<UserResponse> getUserById(@PathVariable @Min(1) Long userId){
        return ResponseData.<UserResponse>builder()
                .status(200)
                .message("User found")
                .data(userService.findById(userId))
                .build();
    }

    @PostMapping
    public ResponseData<Long> createUser(@RequestBody @Valid UserRequestDTO request){
        return ResponseData.<Long>builder()
                .status(201)
                .message("User created")
                .data(userService.create(request))
                .build();
    }

    @PatchMapping("/{userId}/status")
    public ResponseData<String> updateUserStatus(@PathVariable @Min(1) Long userId, @RequestParam String status){
        userService.updateStatus(userId, status);
        return new ResponseData<>(200, "User status updated");
    }

    @PutMapping("/{userId}/email")
    public ResponseData<String> updateEmail(@PathVariable @Min(1) Long userId, @RequestBody @Valid EmailUpdateRequest request){
        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .data(userService.updateEmail(userId, request))
                .build();
    }
    @PutMapping("/{userId}/email/verify")
    public ResponseData<String> verifyUpdateEmail(@PathVariable @Min(1) Long userId, @RequestParam @Size(min = 6, max = 6) String otpCode){
        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .data(userService.verifyUpdateEmail(userId, otpCode))
                .build();
    }

    @PutMapping("/{userId}/phone")
    public ResponseData<String> updatePhone(@PathVariable @Min(1) Long userId, @RequestBody @Valid PhoneUpdateRequest request){
        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .data(userService.updatePhone(userId, request))
                .build();
    }
    @PutMapping("/{userId}/phone/verify")
    public ResponseData<String> verifyUpdatePhone(@PathVariable @Min(1) Long userId, @RequestParam @Size(min = 6, max = 6) String otpCode){
        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .data(userService.verifyUpdatePhone(userId, otpCode))
                .build();
    }

    @PatchMapping("/{userId}/avatar")
    public ResponseData<String> updateAvatar(@PathVariable @Min(1) Long userId, @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseData.<String>builder()
                .status(HttpStatus.OK.value())
                .data(userService.updateAvatar(userId, file))
                .build();
    }

}
