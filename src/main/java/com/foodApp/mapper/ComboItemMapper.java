package com.foodApp.mapper;

import com.foodApp.dto.request.ComboItemRequest;
import com.foodApp.dto.response.ComboItemResponse;
import com.foodApp.entity.ComboItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = {MenuItemMapper.class})
public interface ComboItemMapper {

    @Mapping(target = "comboId", source = "combo.id")
    @Mapping(target = "menuItem", source = "menuItem")
    ComboItemResponse toResponse(ComboItem entity);

    ComboItem toEntity(ComboItemRequest request);

    @Mapping(target = "id", ignore = true)
    void update(ComboItemRequest request,@MappingTarget ComboItem comboItem);
}
