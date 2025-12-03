package app.store.mapper;

import app.store.dto.request.BrandRequest;
import app.store.dto.response.BrandResponse;
import app.store.entity.Brand;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-03T10:17:08+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class BrandMapperImpl implements BrandMapper {

    @Override
    public BrandResponse toBrandResponse(Brand brand) {
        if ( brand == null ) {
            return null;
        }

        BrandResponse.BrandResponseBuilder brandResponse = BrandResponse.builder();

        brandResponse.id( brand.getId() );
        brandResponse.name( brand.getName() );
        brandResponse.description( brand.getDescription() );
        brandResponse.slug( brand.getSlug() );

        brandResponse.productCount( brand.getProducts() != null ? (long) brand.getProducts().size() : 0L );

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
}
