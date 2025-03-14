package com.foodApp.mapper;

import com.foodApp.dto.request.ComboRequest;
import com.foodApp.dto.response.ComboResponse;
import com.foodApp.entity.Combo;
import com.foodApp.entity.ComboItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = {ComboItem.class})
public interface ComboMapper {
    Combo toEntity(ComboRequest request);

    @Mapping(target = "menuCategoryId", source = "menuCategory.id")
    @Mapping(target = "itemIds", expression = "java(entity.getItems() == null ? null : entity.getItems().stream().map(ComboItem::getId).collect(java.util.stream.Collectors.toSet()))")
    ComboResponse toResponse(Combo entity);

    @Mapping(target = "id", ignore = true)
    void update(ComboRequest request,@MappingTarget Combo entity);
}
