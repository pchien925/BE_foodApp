package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import vn.edu.hcmute.foodapp.dto.response.CartItemResponse;
import vn.edu.hcmute.foodapp.entity.CartItem;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartItemMapper {
    CartItemMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CartItemMapper.class);

    CartItemResponse toResponse(CartItem cartItem);
}
