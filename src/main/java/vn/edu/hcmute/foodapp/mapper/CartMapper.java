package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vn.edu.hcmute.foodapp.dto.response.CartResponse;
import vn.edu.hcmute.foodapp.entity.Cart;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE
, uses = {MenuItemOptionMapper.class, CartItemMapper.class, MenuItemMapper.class})
public interface CartMapper {
    CartMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CartMapper.class);

    CartResponse toResponse(Cart cart);
}
