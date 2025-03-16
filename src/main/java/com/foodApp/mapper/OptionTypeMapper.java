package com.foodApp.mapper;

import com.foodApp.dto.request.OptionTypeRequest;
import com.foodApp.dto.response.OptionTypeResponse;
import com.foodApp.entity.MenuItem;
import com.foodApp.entity.OptionType;
import com.foodApp.entity.OptionValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = { MenuItem.class}
, uses = {OptionValueMapper.class})
public interface OptionTypeMapper {
    OptionType toEntity(OptionTypeRequest request);

    @Mapping(target = "optionValues", source = "optionValues")
    OptionTypeResponse toResponse(OptionType entity);

    @Mapping(target = "id", ignore = true)
    void update(OptionTypeRequest request,@MappingTarget OptionType optionType);
}
