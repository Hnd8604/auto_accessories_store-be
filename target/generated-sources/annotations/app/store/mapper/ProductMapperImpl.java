package app.store.mapper;

import app.store.dto.request.ProductCreationRequest;
import app.store.dto.request.ProductUpdateRequest;
import app.store.dto.response.ProductResponse;
import app.store.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-19T23:00:02+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toProduct(ProductCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        Product product = new Product();

        product.setProductName( request.getProductName() );
        product.setDescription( request.getDescription() );
        product.setPrice( request.getPrice() );
        product.setCategory( request.getCategory() );
        product.setStockQuantity( request.getStockQuantity() );

        return product;
    }

    @Override
    public ProductResponse toProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.productId( product.getProductId() );
        productResponse.productName( product.getProductName() );
        productResponse.description( product.getDescription() );
        productResponse.price( product.getPrice() );
        productResponse.category( product.getCategory() );
        productResponse.stockQuantity( product.getStockQuantity() );

        return productResponse.build();
    }

    @Override
    public void updateProduct(Product product, ProductUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        product.setProductName( request.getProductName() );
        product.setDescription( request.getDescription() );
        product.setPrice( request.getPrice() );
        product.setCategory( request.getCategory() );
        product.setStockQuantity( request.getStockQuantity() );
    }
}
