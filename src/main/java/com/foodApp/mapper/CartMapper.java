package com.foodApp.mapper;

import com.foodApp.dto.response.CartResponse;
import com.foodApp.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "userId", source = "user.id")
    CartResponse toResponse(Cart entity);

}
