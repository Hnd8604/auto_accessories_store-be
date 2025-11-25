package app.store.mapper;

import app.store.dto.request.ProductRequest;
import app.store.dto.response.ProductResponse;
import app.store.entity.Brand;
import app.store.entity.Category;
import app.store.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-24T21:26:59+0700",
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
        product.setUnitPrice( request.getUnitPrice() );
        product.setStockQuantity( request.getStockQuantity() );

        return product;
    }

    @Override
    public ProductResponse toProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.categoryName( productCategoryName( product ) );
        productResponse.brandName( productBrandName( product ) );
        productResponse.id( product.getId() );
        productResponse.name( product.getName() );
        productResponse.description( product.getDescription() );
        productResponse.unitPrice( product.getUnitPrice() );
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
        product.setUnitPrice( request.getUnitPrice() );
        product.setStockQuantity( request.getStockQuantity() );
    }

    private String productCategoryName(Product product) {
        if ( product == null ) {
            return null;
        }
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        String name = category.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String productBrandName(Product product) {
        if ( product == null ) {
            return null;
        }
        Brand brand = product.getBrand();
        if ( brand == null ) {
            return null;
        }
        String name = brand.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
