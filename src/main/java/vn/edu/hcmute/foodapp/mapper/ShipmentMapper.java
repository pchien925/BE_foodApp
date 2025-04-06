package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vn.edu.hcmute.foodapp.dto.response.ShipmentInfoResponse;
import vn.edu.hcmute.foodapp.entity.Shipment;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShipmentMapper {
    ShipmentMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(ShipmentMapper.class);

    ShipmentInfoResponse toInfoResponse(Shipment shipment);
}
