package vn.edu.hcmute.foodapp.service;

import vn.edu.hcmute.foodapp.dto.request.BranchRequest;
import vn.edu.hcmute.foodapp.dto.response.BranchDetailsResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;

public interface BranchService {
    BranchDetailsResponse createBranch(BranchRequest request);

    BranchDetailsResponse updateBranch(Integer id, BranchRequest request);

    void deleteBranch(Integer id);

    BranchDetailsResponse getBranchById(Integer id);

    PageResponse<BranchDetailsResponse> getBranches(int page, int size, String sort, String direction);

    PageResponse<BranchDetailsResponse> searchBranches(String keyword, int page, int size, String sort, String direction);
}
