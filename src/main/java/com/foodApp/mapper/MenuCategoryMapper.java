package com.foodApp.mapper;

import com.foodApp.dto.request.MenuCategoryRequest;
import com.foodApp.dto.response.MenuCategoryResponse;
import com.foodApp.entity.Combo;
import com.foodApp.entity.MenuCategory;
import com.foodApp.entity.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", imports = {MenuItem.class, Combo.class})
public interface MenuCategoryMapper {
    @Mapping(target = "itemIds", expression = "java(entity.getItems() == null ? null : entity.getItems().stream().map(MenuItem::getId).collect(java.util.stream.Collectors.toSet()))")
    @Mapping(target = "comboIds", expression = "java(entity.getCombos() == null ? null : entity.getCombos().stream().map(Combo::getId).collect(java.util.stream.Collectors.toSet()))")
    MenuCategoryResponse toResponse(MenuCategory entity);
    MenuCategory toEntity(MenuCategoryRequest request);

    @Mapping(target = "id", ignore = true)
    void update(MenuCategoryRequest request, @MappingTarget MenuCategory entity);
}
