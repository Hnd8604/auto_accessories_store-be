package app.store.mapper;

import app.store.dto.request.BranchRequest;
import app.store.dto.response.BranchResponse;
import app.store.dto.response.ProductResponse;
import app.store.entity.Branch;
import app.store.entity.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-05T22:15:35+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class BranchMapperImpl implements BranchMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public BranchResponse toBranchResponse(Branch branch) {
        if ( branch == null ) {
            return null;
        }

        BranchResponse.BranchResponseBuilder branchResponse = BranchResponse.builder();

        branchResponse.products( productListToProductResponseList( branch.getProducts() ) );
        branchResponse.id( branch.getId() );
        branchResponse.name( branch.getName() );
        branchResponse.description( branch.getDescription() );

        return branchResponse.build();
    }

    @Override
    public Branch toBranch(BranchRequest branchRequest) {
        if ( branchRequest == null ) {
            return null;
        }

        Branch.BranchBuilder branch = Branch.builder();

        branch.name( branchRequest.getName() );
        branch.description( branchRequest.getDescription() );

        return branch.build();
    }

    @Override
    public void updateBranch(Branch branch, BranchRequest branchRequest) {
        if ( branchRequest == null ) {
            return;
        }

        branch.setName( branchRequest.getName() );
        branch.setDescription( branchRequest.getDescription() );
    }

    protected List<ProductResponse> productListToProductResponseList(List<Product> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductResponse> list1 = new ArrayList<ProductResponse>( list.size() );
        for ( Product product : list ) {
            list1.add( productMapper.toProductResponse( product ) );
        }

        return list1;
    }
}
