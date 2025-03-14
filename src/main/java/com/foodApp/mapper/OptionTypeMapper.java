package com.foodApp.mapper;

import com.foodApp.dto.request.OptionTypeRequest;
import com.foodApp.dto.response.OptionTypeResponse;
import com.foodApp.entity.MenuItem;
import com.foodApp.entity.OptionType;
import com.foodApp.entity.OptionValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = {OptionValue.class, MenuItem.class})
public interface OptionTypeMapper {
    OptionType toEntity(OptionTypeRequest request);

    @Mapping(target = "menuItemIds", expression = "java(entity.getMenuItems() == null ? null : entity.getMenuItems().stream().map(MenuItem::getId).collect(java.util.stream.Collectors.toSet()))")
    @Mapping(target = "optionValueIds", expression = "java(entity.getOptionValues() == null ? null : entity.getOptionValues().stream().map(OptionValue::getId).collect(java.util.stream.Collectors.toSet()))")
    OptionTypeResponse toResponse(OptionType entity);

    @Mapping(target = "id", ignore = true)
    void update(OptionTypeRequest request,@MappingTarget OptionType optionType);
}
