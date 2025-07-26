package app.store.mapper;

import app.store.dto.request.ProductRequest;
import app.store.dto.response.ProductResponse;
import app.store.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", ignore = true)
        // Ignore category during product creation so we have to set it manually
    Product toProduct(ProductRequest request);
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true) // Giữ nguyên createdAt
    void updateProduct(@MappingTarget Product product, ProductRequest request);
}