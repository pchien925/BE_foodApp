package com.foodApp.mapper;

import com.foodApp.dto.request.MenuItemRequest;
import com.foodApp.dto.response.MenuItemResponse;
import com.foodApp.entity.MenuItem;
import com.foodApp.entity.OptionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", imports = {OptionType.class},
uses = {OptionTypeMapper.class})
public interface MenuItemMapper {
    @Mapping(target = "menuCategoryId", source = "menuCategory.id")
    @Mapping(target = "optionTypes", source = "optionTypes")
    MenuItemResponse toResponse(MenuItem entity);
    MenuItem toEntity(MenuItemRequest request);

    @Mapping(target = "id", ignore = true)
    void update(MenuItemRequest request, @MappingTarget MenuItem entity);
}
