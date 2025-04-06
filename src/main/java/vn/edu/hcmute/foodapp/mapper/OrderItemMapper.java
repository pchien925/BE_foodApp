package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import vn.edu.hcmute.foodapp.dto.response.OrderItemResponse;
import vn.edu.hcmute.foodapp.entity.OrderItem;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {OrderItemOptionMapper.class}
)
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(OrderItemMapper.class);

    OrderItemResponse toResponse(OrderItem orderItem);
}
