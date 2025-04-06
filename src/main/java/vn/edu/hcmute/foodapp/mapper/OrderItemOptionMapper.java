package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vn.edu.hcmute.foodapp.dto.response.OrderItemOptionResponse;
import vn.edu.hcmute.foodapp.entity.OrderItemOption;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemOptionMapper {
    OrderItemOptionMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(OrderItemOptionMapper.class);

    OrderItemOptionResponse toResponse(OrderItemOption orderItemOption);
}
