package app.store.mapper;

import app.store.dto.request.BrandRequest;
import app.store.dto.response.BrandResponse;
import app.store.dto.response.ProductResponse;
import app.store.entity.Brand;
import app.store.entity.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-18T15:00:11+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class BrandMapperImpl implements BrandMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public BrandResponse toBrandResponse(Brand brand) {
        if ( brand == null ) {
            return null;
        }

        BrandResponse.BrandResponseBuilder brandResponse = BrandResponse.builder();

        brandResponse.products( productListToProductResponseList( brand.getProducts() ) );
        brandResponse.id( brand.getId() );
        brandResponse.name( brand.getName() );
        brandResponse.description( brand.getDescription() );

        return brandResponse.build();
    }

    @Override
    public Brand toBrand(BrandRequest brandRequest) {
        if ( brandRequest == null ) {
            return null;
        }

        Brand.BrandBuilder brand = Brand.builder();

        brand.name( brandRequest.getName() );
        brand.description( brandRequest.getDescription() );

        return brand.build();
    }

    @Override
    public void updateBrand(Brand brand, BrandRequest brandRequest) {
        if ( brandRequest == null ) {
            return;
        }

        brand.setName( brandRequest.getName() );
        brand.setDescription( brandRequest.getDescription() );
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
