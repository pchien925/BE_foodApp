package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vn.edu.hcmute.foodapp.dto.response.ShipmentDetailResponse;
import vn.edu.hcmute.foodapp.dto.response.ShipmentInfoResponse;
import vn.edu.hcmute.foodapp.entity.Shipment;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
uses = {ShipmentTrackingEventMapper.class})
public interface ShipmentMapper {
    ShipmentMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(ShipmentMapper.class);

    ShipmentDetailResponse toDetailResponse(Shipment shipment);

    ShipmentInfoResponse toInfoResponse(Shipment shipment);
}
