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

    @Mapping(target = "roles", expression = "java(entity.getRoles().stream().map(role -> role.getRole().getName()).collect(java.util.stream.Collectors.toList()))")
    UserResponse toResponse(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "password", ignore = true)
    void update(UserRequestDTO request, @MappingTarget User user);
}
