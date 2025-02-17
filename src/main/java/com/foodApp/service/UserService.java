package com.foodApp.service;

import com.foodApp.dto.request.UserRequestDTO;
import com.foodApp.dto.response.UserResponse;

public interface UserService {

    Long create(UserRequestDTO request);

    void updateStatus(long userId, String status);

    UserResponse findById(long userId);
}
