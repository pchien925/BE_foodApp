package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import vn.edu.hcmute.foodapp.dto.response.CartItemResponse;
import vn.edu.hcmute.foodapp.entity.CartItem;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
        MenuItemOptionMapper.class,
        MenuItemMapper.class})
public interface CartItemMapper {
    CartItemMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CartItemMapper.class);

    @Mapping(target = "selectedOptions", expression = "java(cartItem.getCartItemOptions().stream().map(option -> MenuItemOptionMapper.INSTANCE.toResponse(option.getMenuItemOption())).collect(java.util.stream.Collectors.toSet()))")
    CartItemResponse toResponse(CartItem cartItem);
}
