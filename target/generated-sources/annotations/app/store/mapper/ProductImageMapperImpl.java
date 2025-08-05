package app.store.mapper;

import app.store.dto.request.ProductImageRequest;
import app.store.dto.response.ProductImageResponse;
import app.store.entity.Product;
import app.store.entity.ProductImage;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-05T22:15:35+0700",
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
        productImageResponse.description( productImage.getDescription() );

        return productImageResponse.build();
    }

    @Override
    public ProductImage toProductImage(ProductImageRequest request) {
        if ( request == null ) {
            return null;
        }

        ProductImage productImage = new ProductImage();

        productImage.setImageUrl( request.getImageUrl() );
        productImage.setDescription( request.getDescription() );

        return productImage;
    }

    @Override
    public ProductImage updateProductImage(ProductImage productImage, ProductImageRequest request) {
        if ( request == null ) {
            return productImage;
        }

        productImage.setImageUrl( request.getImageUrl() );
        productImage.setDescription( request.getDescription() );

        return productImage;
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
