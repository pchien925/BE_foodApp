package vn.edu.hcmute.foodapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import vn.edu.hcmute.foodapp.dto.request.BranchRequest;
import vn.edu.hcmute.foodapp.dto.response.BranchResponse;
import vn.edu.hcmute.foodapp.entity.Branch;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BranchMapper {
    BranchMapper INSTANCE = Mappers.getMapper(BranchMapper.class);

    Branch toEntity(BranchRequest branchRequest);

    BranchResponse toResponse(Branch branch);

    @Mapping(target = "id", ignore = true)
    void update(BranchRequest branchRequest, @MappingTarget Branch branch);
}
