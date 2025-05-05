package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import vn.edu.hcmute.foodapp.dto.request.SignUpRequest;
import vn.edu.hcmute.foodapp.dto.request.UpdateUserRequest;
import vn.edu.hcmute.foodapp.dto.response.UserInfoResponse;
import vn.edu.hcmute.foodapp.dto.response.UserResponse;
import vn.edu.hcmute.foodapp.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(SignUpRequest request);

    UserResponse toResponse(User user);

    UserInfoResponse toUserInfoResponse(User user);

    void update(@MappingTarget User user, UpdateUserRequest request);
}
