package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import vn.edu.hcmute.foodapp.dto.request.SignUpRequest;
import vn.edu.hcmute.foodapp.dto.response.UserResponse;
import vn.edu.hcmute.foodapp.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(SignUpRequest request);

    @Mapping(target = "roles", expression = "java(user.getRoles() != null ? user.getRoles().stream().map(uhr -> uhr.getRole().getName().toString()).collect(java.util.stream.Collectors.toList()) : null)")
    UserResponse toResponse(User user);
}
