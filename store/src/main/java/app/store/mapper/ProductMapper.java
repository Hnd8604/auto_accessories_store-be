package app.store.mapper;

import app.store.dto.request.ProductCreationRequest;
import app.store.dto.request.ProductUpdateRequest;
import app.store.dto.request.UserUpdateRequest;
import app.store.dto.response.ProductResponse;
import app.store.dto.response.UserResponse;
import app.store.entity.Product;
import app.store.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductCreationRequest request);
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "productId", ignore = true) // Ignore the ID field during update
    void updateProduct(@MappingTarget Product product, ProductUpdateRequest request);
}
