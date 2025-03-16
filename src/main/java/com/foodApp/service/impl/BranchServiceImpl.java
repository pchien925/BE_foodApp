package com.foodApp.service.impl;

import com.foodApp.dto.request.BranchRequest;
import com.foodApp.dto.response.BranchResponse;
import com.foodApp.entity.Branch;
import com.foodApp.exception.ResourceNotFoundException;
import com.foodApp.mapper.BranchMapper;
import com.foodApp.repository.BranchRepository;
import com.foodApp.service.BranchService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    @Override
    public Branch findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return branchRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Branch not found with id: " + id)
        );
    }

    @Transactional
    @Override
    public BranchResponse createBranch(BranchRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Branch branch = branchMapper.toEntity(request);
        return branchMapper.toResponse(branchRepository.save(branch));

    }

    @Transactional
    @Override
    public BranchResponse updateBranch(Long id, BranchRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        Branch branch = findById(id);

        branchMapper.update(request, branch);

        return branchMapper.toResponse(branchRepository.save(branch));
    }

    @Transactional
    @Override
    public void deleteBranch(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Branch branch = findById(id);
        branchRepository.delete(branch);
    }

    @Override
    public BranchResponse getBranch(Long id) {
        return branchMapper.toResponse(findById(id));
    }

    @Override
    public List<BranchResponse> getBranches() {
        return branchRepository.findAll().stream().map(branchMapper::toResponse).toList();
    }
}
