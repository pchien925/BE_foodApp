package com.foodApp.mapper;

import com.foodApp.dto.request.BranchRequest;
import com.foodApp.dto.response.BranchResponse;
import com.foodApp.entity.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BranchMapper {
    Branch toEntity(BranchRequest request);
    BranchResponse toResponse(Branch entity);

    @Mapping(target = "id", ignore = true)
    void update(BranchRequest request,@MappingTarget Branch entity);
}
