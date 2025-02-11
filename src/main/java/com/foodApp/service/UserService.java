package com.foodApp.service;

import com.foodApp.dto.request.UserRequestDTO;

public interface UserService {

    Long create(UserRequestDTO request);

    void updateStatus(long userId, String status);

    void verifyUser(String username, Integer verificationCode);
}
