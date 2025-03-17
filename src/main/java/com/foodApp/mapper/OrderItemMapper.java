package com.foodApp.mapper;

import com.foodApp.dto.request.OrderItemRequest;
import com.foodApp.dto.response.OrderItemResponse;
import com.foodApp.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MenuItemMapper.class, OptionValueMapper.class})
public interface OrderItemMapper {
    @Mapping(target = "menuItem", source = "menuItem")
    @Mapping(target = "selectedOptions", source = "selectedOptions")
    OrderItemResponse toResponse(OrderItem entity);

    @Mapping(target = "selectedOptions", ignore = true)
    @Mapping(target = "menuItem", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemRequest request);
}
