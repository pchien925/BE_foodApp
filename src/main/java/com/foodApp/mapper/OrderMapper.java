package com.foodApp.mapper;

import com.foodApp.dto.request.OrderRequest;
import com.foodApp.dto.response.OrderResponse;
import com.foodApp.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class, PaymentMapper.class})
public interface OrderMapper {

    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "loyaltyPointsEarned", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "payment", ignore = true)
    Order toEntity(OrderRequest request);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "branchId", source = "branch.id")
    @Mapping(target = "orderItems", source = "orderItems")
    @Mapping(target = "payment", source = "payment")
    OrderResponse toResponse(Order entity);
}
