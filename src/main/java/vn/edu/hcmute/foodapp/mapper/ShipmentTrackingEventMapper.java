package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vn.edu.hcmute.foodapp.dto.response.ShipmentTrackingEventResponse;
import vn.edu.hcmute.foodapp.entity.ShipmentTrackingEvent;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShipmentTrackingEventMapper {
    ShipmentTrackingEventMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(ShipmentTrackingEventMapper.class);

    ShipmentTrackingEventResponse toResponse(ShipmentTrackingEvent shipmentTrackingEvent);
}
