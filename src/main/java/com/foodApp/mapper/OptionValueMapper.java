package com.foodApp.mapper;

import com.foodApp.dto.request.OptionValueRequest;
import com.foodApp.dto.response.OptionValueResponse;
import com.foodApp.entity.OptionValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OptionValueMapper {
    OptionValue toEntity(OptionValueRequest request);

    @Mapping(target = "optionTypeId", source = "optionType.id")
    OptionValueResponse toResponse(OptionValue entity);

    @Mapping(target = "id", ignore = true)
    void update(OptionValueRequest request,@MappingTarget OptionValue optionValue);
}
