package com.foodApp.mapper;

import com.foodApp.dto.request.CartItemRequest;
import com.foodApp.dto.response.CartItemResponse;
import com.foodApp.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MenuItemMapper.class, OptionValueMapper.class})
public interface CartItemMapper {
    @Mapping(target = "menuItem", source = "menuItem")
    @Mapping(target = "selectedOptions", source = "selectedOptions")
    CartItemResponse toResponse(CartItem entity);

    @Mapping(target = "selectedOptions", ignore = true)
    @Mapping(target = "menuItem", ignore = true)
    @Mapping(target = "cart", ignore = true)
    CartItem toEntity(CartItemRequest request);
}
