package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import vn.edu.hcmute.foodapp.dto.request.MenuCategoryRequest;
import vn.edu.hcmute.foodapp.dto.response.MenuCategoryResponse;
import vn.edu.hcmute.foodapp.entity.MenuCategory;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuCategoryMapper {
    MenuCategoryMapper INSTANCE = Mappers.getMapper(MenuCategoryMapper.class);

    MenuCategory toEntity(MenuCategoryRequest request);

    MenuCategoryResponse toResponse(MenuCategory menuCategory);

    @Mapping(target = "id", ignore = true)
    void update(MenuCategoryRequest request, @MappingTarget MenuCategory menuCategory);
}
