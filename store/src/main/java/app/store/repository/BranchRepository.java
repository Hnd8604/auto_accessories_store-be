package app.store.repository;

import app.store.dto.response.BranchResponse;
import app.store.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Branch findByName(String name);
}
