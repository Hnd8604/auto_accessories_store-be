package app.store.mapper;

import app.store.dto.request.ProductRequest;
import app.store.dto.response.ProductResponse;
import app.store.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-26T17:21:28+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toProduct(ProductRequest request) {
        if ( request == null ) {
            return null;
        }

        Product product = new Product();

        product.setName( request.getName() );
        product.setDescription( request.getDescription() );
        product.setPrice( request.getPrice() );
        product.setStockQuantity( request.getStockQuantity() );

        return product;
    }

    @Override
    public ProductResponse toProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.id( product.getId() );
        productResponse.name( product.getName() );
        productResponse.description( product.getDescription() );
        if ( product.getPrice() != null ) {
            productResponse.price( product.getPrice().doubleValue() );
        }
        productResponse.category( product.getCategory() );
        productResponse.stockQuantity( product.getStockQuantity() );

        return productResponse.build();
    }

    @Override
    public void updateProduct(Product product, ProductRequest request) {
        if ( request == null ) {
            return;
        }

        product.setName( request.getName() );
        product.setDescription( request.getDescription() );
        product.setPrice( request.getPrice() );
        product.setStockQuantity( request.getStockQuantity() );
    }
}
