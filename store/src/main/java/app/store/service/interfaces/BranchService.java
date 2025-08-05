package app.store.service.interfaces;

import app.store.dto.request.BranchRequest;
import app.store.dto.response.BranchResponse;

import java.util.List;

public interface BranchService {
    List<BranchResponse> getAllBranches();
    BranchResponse getBranchById(Long id);
    BranchResponse createBranch(BranchRequest branchRequest);
    BranchResponse updateBranch(Long id, BranchRequest branchRequest);
    void deleteBranch(Long id);


}
