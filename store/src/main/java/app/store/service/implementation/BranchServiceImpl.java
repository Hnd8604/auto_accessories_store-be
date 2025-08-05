package app.store.service.implementation;

import app.store.dto.request.BranchRequest;
import app.store.dto.response.BranchResponse;
import app.store.entity.Branch;
import app.store.mapper.BranchMapper;
import app.store.repository.BranchRepository;
import app.store.service.interfaces.BranchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BranchServiceImpl implements BranchService {
    BranchRepository branchRepository;
    BranchMapper branchMapper;
    @Override
    public List<BranchResponse> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(branchMapper::toBranchResponse).toList();
    }

    @Override
    public BranchResponse getBranchById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
        return branchMapper.toBranchResponse(branch);

    }

    @Override
    public BranchResponse createBranch(BranchRequest branchRequest) {
        Branch branch = branchMapper.toBranch(branchRequest);
        return branchMapper.toBranchResponse(branchRepository.save(branch));
    }

    @Override
    public BranchResponse updateBranch(Long id, BranchRequest branchRequest) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
        branchMapper.updateBranch(branch, branchRequest);
        return branchMapper.toBranchResponse(branchRepository.save(branch));
    }

    @Override
    public void deleteBranch(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
        branchRepository.delete(branch);

    }
}
