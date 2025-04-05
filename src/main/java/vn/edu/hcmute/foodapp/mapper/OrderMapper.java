package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vn.edu.hcmute.foodapp.dto.request.CreateOrderRequest;
import vn.edu.hcmute.foodapp.dto.response.OrderInfoResponse;
import vn.edu.hcmute.foodapp.entity.Order;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    OrderMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(OrderMapper.class);

    Order toEntity(CreateOrderRequest orderRequest);

    OrderInfoResponse toOrderInfoResponse(Order order);
}
