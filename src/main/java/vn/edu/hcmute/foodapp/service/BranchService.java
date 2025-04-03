package vn.edu.hcmute.foodapp.service;

import vn.edu.hcmute.foodapp.dto.request.BranchRequest;
import vn.edu.hcmute.foodapp.dto.response.BranchResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;

public interface BranchService {
    BranchResponse createBranch(BranchRequest request);

    BranchResponse updateBranch(Integer id, BranchRequest request);

    void deleteBranch(Integer id);

    BranchResponse getBranchById(Integer id);

    PageResponse<BranchResponse> getBranches(int page, int size, String sort, String direction);

    PageResponse<BranchResponse> searchBranches(String keyword, int page, int size, String sort, String direction);
}
