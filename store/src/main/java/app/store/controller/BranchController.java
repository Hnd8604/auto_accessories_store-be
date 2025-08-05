package app.store.controller;

import app.store.dto.request.BranchRequest;
import app.store.dto.response.BranchResponse;
import app.store.dto.response.CategoryResponse;
import app.store.dto.response.auth.ApiResponse;
import app.store.service.implementation.BranchServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BranchController {
    BranchServiceImpl branchServiceImpl;

    @GetMapping
    ApiResponse<List<BranchResponse>> getAllBranches() {
        return ApiResponse.<List<BranchResponse>>builder()
                .result(branchServiceImpl.getAllBranches())
                .build();
    }
    @GetMapping("/{id}")
    ApiResponse<BranchResponse> getBranchById(@PathVariable String id) {
        return ApiResponse.<BranchResponse>builder()
                .result(branchServiceImpl.getBranchById(Long.parseLong(id)))
                .build();
    }
    @PostMapping
    ApiResponse<BranchResponse> createBranch(@RequestBody BranchRequest branchRequest) {
        return ApiResponse.<BranchResponse>builder()
                .result(branchServiceImpl.createBranch(branchRequest))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<BranchResponse> updateBranch(@PathVariable Long id,@RequestBody BranchRequest branchRequest) {
        return ApiResponse.<BranchResponse>builder()
                .result(branchServiceImpl.updateBranch(id, branchRequest))
                .build();
    }
    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteBranch(@PathVariable Long id) {
        branchServiceImpl.deleteBranch(id);
        return ApiResponse.<Void>builder()
                .build();
        }
}
