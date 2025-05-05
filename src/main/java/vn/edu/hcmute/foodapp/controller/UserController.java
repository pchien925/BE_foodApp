package vn.edu.hcmute.foodapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmute.foodapp.dto.request.UpdateUserRequest;
import vn.edu.hcmute.foodapp.dto.response.ResponseData;
import vn.edu.hcmute.foodapp.dto.response.UserResponse;
import vn.edu.hcmute.foodapp.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
@Slf4j(topic = "USER_CONTROLLER")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseData<UserResponse> getCurrentUser() {
        log.info("Get current user request");
        return ResponseData.<UserResponse>builder()
                .data(userService.getCurrentUser())
                .message("Current user retrieved successfully")
                .build();
    }

    @PutMapping("/{userId}")
    public ResponseData<UserResponse> updateUser(@PathVariable Long userId,
                                                 @RequestBody @Valid UpdateUserRequest request) {
        log.info("Update user request");
        return ResponseData.<UserResponse>builder()
                .data(userService.updateUser(userId, request))
                .message("User updated successfully")
                .build();
    }
}
