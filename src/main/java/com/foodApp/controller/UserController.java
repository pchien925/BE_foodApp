package com.foodApp.controller;

import com.foodApp.dto.request.UserRequestDTO;
import com.foodApp.dto.response.ResponseData;
import com.foodApp.entity.User;
import com.foodApp.exception.ResourceNotFoundException;
import com.foodApp.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseData<String> getUserById(@PathVariable @Min(1) Long userId){
        if (userId == 1){
        throw new ResourceNotFoundException("User not found");
        }
        return new ResponseData<>(200, "User found", "user");
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

    @PatchMapping("/verifyUser")
    public ResponseData<String> verifyUser(@RequestParam String username, @RequestParam Integer verificationCode){
        userService.verifyUser(username, verificationCode);
        return new ResponseData<>(200, "User verified");
    }
}
