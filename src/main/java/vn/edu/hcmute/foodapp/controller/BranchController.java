package vn.edu.hcmute.foodapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmute.foodapp.dto.request.BranchRequest;
import vn.edu.hcmute.foodapp.dto.response.BranchDetailsResponse;
import vn.edu.hcmute.foodapp.dto.response.PageResponse;
import vn.edu.hcmute.foodapp.dto.response.ResponseData;
import vn.edu.hcmute.foodapp.service.BranchService;


@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
@Tag(name = "Branch", description = "Branch API")
@Slf4j(topic = "BRANCH_CONTROLLER")
public class BranchController {
    private final BranchService branchService;

    @GetMapping("/{id}")
    @Operation(summary = "Get branch by ID", description = "Retrieve a branch by its ID")
    public ResponseData<BranchDetailsResponse> getBranchById(@PathVariable("id") Integer id) {
        log.info("Get branch request for id: {}", id);
        return ResponseData.<BranchDetailsResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Branch retrieved successfully")
                .data(branchService.getBranchById(id))
                .build();
    }

    @PostMapping
    @Operation(summary = "Create a new branch", description = "Create a new branch with provided details")
    public ResponseData<BranchDetailsResponse> createBranch(@RequestBody @Valid BranchRequest request) {
        log.info("Create branch request: {}", request);
        return ResponseData.<BranchDetailsResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Branch created successfully")
                .data(branchService.createBranch(request))
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a branch", description = "Update an existing branch by its ID")
    public ResponseData<BranchDetailsResponse> updateBranch(
            @PathVariable("id") Integer id,
            @RequestBody @Valid BranchRequest request) {
        log.info("Update branch request for id: {}", id);
        return ResponseData.<BranchDetailsResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Branch updated successfully")
                .data(branchService.updateBranch(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a branch", description = "Delete a branch by its ID")
    public ResponseData<Void> deleteBranch(@PathVariable("id") Integer id) {
        log.info("Delete branch request for id: {}", id);
        branchService.deleteBranch(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Branch deleted successfully")
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all branches", description = "Retrieve a paginated list of all branches")
    public ResponseData<PageResponse<BranchDetailsResponse>> getAllBranches(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Get all branches request: page={}, size={}, sort={}, direction={}", page, size, sort, direction);
        return ResponseData.<PageResponse<BranchDetailsResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Branches retrieved successfully")
                .data(branchService.getBranches(page, size, sort, direction))
                .build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search branches", description = "Search branches by keyword with pagination")
    public ResponseData<PageResponse<BranchDetailsResponse>> searchBranches(
            @Parameter(description = "Keyword to search in name or description") @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Search branches request with keyword: {}", keyword);
        return ResponseData.<PageResponse<BranchDetailsResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Branches search completed successfully")
                .data(branchService.searchBranches(keyword, page, size, sort, direction))
                .build();
    }
}