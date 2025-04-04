package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import vn.edu.hcmute.foodapp.dto.response.CartResponse;
import vn.edu.hcmute.foodapp.entity.Cart;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE
, uses = {CartItemMapper.class})
public interface CartMapper {
    CartMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CartMapper.class);

    @Mapping(target = "userId", source = "user.id")
    CartResponse toResponse(Cart cart);
}
