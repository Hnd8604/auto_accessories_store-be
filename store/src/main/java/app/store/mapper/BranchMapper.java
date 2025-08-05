package app.store.mapper;

import app.store.dto.request.BranchRequest;
import app.store.dto.response.BranchResponse;
import app.store.entity.Branch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",uses = {ProductMapper.class})
public interface BranchMapper {

    @Mapping(target = "products", source = "products")
    BranchResponse toBranchResponse(Branch branch);

    @Mapping(target = "products", ignore = true)
    Branch toBranch(BranchRequest branchRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateBranch(@MappingTarget Branch branch, BranchRequest branchRequest);
}
