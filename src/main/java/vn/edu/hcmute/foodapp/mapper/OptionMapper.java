package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import vn.edu.hcmute.foodapp.dto.request.OptionRequest;
import vn.edu.hcmute.foodapp.dto.response.OptionResponse;
import vn.edu.hcmute.foodapp.entity.Option;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {MenuItemOptionMapper.class})
public interface OptionMapper {
    OptionMapper INSTANCE = Mappers.getMapper(OptionMapper.class);

     Option toEntity(OptionRequest optionRequest);
     OptionResponse toResponse(Option option);

     @Mapping(target = "id", ignore = true)
     void update(OptionRequest optionRequest, @MappingTarget Option option);
}
