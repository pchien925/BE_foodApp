package com.foodApp.mapper;

import com.foodApp.dto.request.RegisterRequest;
import com.foodApp.dto.request.UserRequestDTO;
import com.foodApp.dto.response.UserResponse;
import com.foodApp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterRequest request);
    User toEntity(UserRequestDTO request);
    UserResponse toResponse(User user);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "password", ignore = true)
    void update(UserRequestDTO request, @MappingTarget User user);
}
