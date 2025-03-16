package com.foodApp.service;

import com.foodApp.dto.request.BranchRequest;
import com.foodApp.dto.response.BranchResponse;
import com.foodApp.entity.Branch;

import java.util.List;

public interface BranchService {
    Branch findById(Long id);
    BranchResponse createBranch(BranchRequest request);
    BranchResponse updateBranch(Long id, BranchRequest request);
    void deleteBranch(Long id);
    BranchResponse getBranch(Long id);
    List<BranchResponse> getBranches();
}
