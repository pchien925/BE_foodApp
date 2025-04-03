package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import vn.edu.hcmute.foodapp.dto.request.MenuItemOptionRequest;
import vn.edu.hcmute.foodapp.dto.response.MenuItemOptionResponse;
import vn.edu.hcmute.foodapp.entity.MenuItemOption;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuItemOptionMapper {
    MenuItemOptionMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(MenuItemOptionMapper.class);

     MenuItemOption toEntity(MenuItemOptionRequest request);

     MenuItemOptionResponse toResponse(MenuItemOption menuItemOption);

     @Mapping(target = "id", ignore = true)
     void update(MenuItemOptionRequest request, @MappingTarget MenuItemOption menuItemOption);
}
