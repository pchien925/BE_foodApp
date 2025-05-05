package vn.edu.hcmute.foodapp.service;

import vn.edu.hcmute.foodapp.dto.request.UpdateUserRequest;
import vn.edu.hcmute.foodapp.dto.response.UserResponse;

public interface UserService {
    UserResponse getUserById(Long id);

    UserResponse getCurrentUser();

    UserResponse updateUser(Long userId, UpdateUserRequest request);
}
