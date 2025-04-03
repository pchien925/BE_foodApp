package vn.edu.hcmute.foodapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.hcmute.foodapp.dto.request.BranchRequest;
import vn.edu.hcmute.foodapp.dto.response.BranchResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;
import vn.edu.hcmute.foodapp.entity.Branch;
import vn.edu.hcmute.foodapp.exception.ResourceNotFoundException;
import vn.edu.hcmute.foodapp.mapper.BranchMapper;
import vn.edu.hcmute.foodapp.repository.BranchRepository;
import vn.edu.hcmute.foodapp.service.BranchService;
import vn.edu.hcmute.foodapp.util.PaginationUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;

    @Override
    public BranchResponse createBranch(BranchRequest request) {
        log.info("Create branch with name: {}", request.getName());
        Branch branch = BranchMapper.INSTANCE.toEntity(request);

        return BranchMapper.INSTANCE.toResponse(branchRepository.save(branch));
    }

    @Override
    public BranchResponse updateBranch(Integer id, BranchRequest request) {
        log.info("Update branch with id: {}", id);
        Branch branch = branchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
        BranchMapper.INSTANCE.update(request, branch);
        return BranchMapper.INSTANCE.toResponse(branchRepository.save(branch));
    }

    @Override
    public void deleteBranch(Integer id) {
        log.info("Delete branch with id: {}", id);
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + id));
        branchRepository.delete(branch);
    }

    @Override
    public BranchResponse getBranchById(Integer id) {
        log.info("Get branch with id: {}", id);
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + id));
        return BranchMapper.INSTANCE.toResponse(branch);
    }

    @Override
    public PageResponse<BranchResponse> getBranches(int page, int size, String sort, String direction) {
        log.info("Fetching all branches with pagination: page={}, size={}, sort={}, direction={}", page, size, sort, direction);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<Branch> branchPage = branchRepository.findAll(pageable);
        return PageResponse.<BranchResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(branchPage.getTotalPages())
                .totalElements(branchPage.getTotalElements())
                .content(branchPage.getContent().stream().map(BranchMapper.INSTANCE::toResponse).toList())
                .build();
    }

    @Override
    public PageResponse<BranchResponse> searchBranches(String keyword, int page, int size, String sort, String direction) {
        log.info("Searching doctors with keyword: {}, page={}, size={}, sort={}, direction={}", keyword, page, size, sort, direction);
        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);

        Page<Branch> branchPage = branchRepository.searchBranches(keyword, pageable);
        return PageResponse.<BranchResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(branchPage.getTotalPages())
                .totalElements(branchPage.getTotalElements())
                .content(branchPage.getContent().stream().map(BranchMapper.INSTANCE::toResponse).toList())
                .build();
    }
}
