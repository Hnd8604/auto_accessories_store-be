package app.store.mapper;

import app.store.dto.request.ProductImageRequest;
import app.store.dto.request.ProductImageUpdateRequest;
import app.store.dto.response.ProductImageResponse;
import app.store.entity.Product;
import app.store.entity.ProductImage;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-15T10:32:39+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class ProductImageMapperImpl implements ProductImageMapper {

    @Override
    public ProductImageResponse toProductImageResponse(ProductImage productImage) {
        if ( productImage == null ) {
            return null;
        }

        ProductImageResponse.ProductImageResponseBuilder productImageResponse = ProductImageResponse.builder();

        productImageResponse.productId( productImageProductId( productImage ) );
        productImageResponse.id( productImage.getId() );
        productImageResponse.imageUrl( productImage.getImageUrl() );
        productImageResponse.altText( productImage.getAltText() );
        productImageResponse.isPrimary( productImage.getIsPrimary() );
        productImageResponse.sortOrder( productImage.getSortOrder() );

        return productImageResponse.build();
    }

    @Override
    public ProductImage toProductImage(ProductImageRequest request) {
        if ( request == null ) {
            return null;
        }

        ProductImage productImage = new ProductImage();

        productImage.setImageUrl( request.getImageUrl() );
        productImage.setAltText( request.getAltText() );
        productImage.setIsPrimary( request.getIsPrimary() );
        productImage.setSortOrder( request.getSortOrder() );

        return productImage;
    }

    @Override
    public void updateProductImage(ProductImage productImage, ProductImageUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        productImage.setImageUrl( request.getImageUrl() );
        productImage.setAltText( request.getAltText() );
        productImage.setIsPrimary( request.getIsPrimary() );
        productImage.setSortOrder( request.getSortOrder() );
    }

    private Long productImageProductId(ProductImage productImage) {
        if ( productImage == null ) {
            return null;
        }
        Product product = productImage.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
