package com.foodApp.controller;

import com.foodApp.dto.request.BranchRequest;
import com.foodApp.dto.response.BranchResponse;
import com.foodApp.dto.response.ResponseData;
import com.foodApp.service.BranchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @GetMapping("/{id}")
    public ResponseData<BranchResponse> get(@PathVariable @Min(value = 0, message = "Invalid branch ID") Long id){
        return ResponseData.<BranchResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Branch retrieved")
                .data(branchService.getBranch(id))
                .build();
    }

    @PostMapping
    public ResponseData<BranchResponse> create(@RequestBody @Valid BranchRequest request){
        return ResponseData.<BranchResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Branch created")
                .data(branchService.createBranch(request))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseData<BranchResponse> update(@PathVariable @Min(value = 0, message = "Invalid branch ID") Long id, @RequestBody @Valid BranchRequest request){
        return ResponseData.<BranchResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Branch updated")
                .data(branchService.updateBranch(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseData<Void> delete(@PathVariable @Min(value = 0, message = "Invalid branch ID") Long id){
        branchService.deleteBranch(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Branch deleted")
                .build();
    }

    @GetMapping
    public ResponseData<List<BranchResponse>> getAllBranches(){
        return ResponseData.<List<BranchResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Branches retrieved")
                .data(branchService.getBranches())
                .build();
    }
}
