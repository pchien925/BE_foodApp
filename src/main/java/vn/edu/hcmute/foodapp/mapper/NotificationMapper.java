package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import vn.edu.hcmute.foodapp.dto.request.NotificationRequest;
import vn.edu.hcmute.foodapp.dto.response.NotificationResponse;
import vn.edu.hcmute.foodapp.entity.Notification;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {
    NotificationMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(NotificationMapper.class);

    @Mapping(target = "userId", source = "userId.id")
    NotificationResponse toResponse(Notification notification);

    Notification toEntity(NotificationRequest request);
}
