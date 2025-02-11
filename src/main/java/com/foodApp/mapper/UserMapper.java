package com.foodApp.mapper;

import com.foodApp.dto.request.RegisterRequest;
import com.foodApp.dto.response.UserResponse;
import com.foodApp.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterRequest request);
    UserResponse toResponse(User user);
}
