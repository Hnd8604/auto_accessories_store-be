package app.store.mapper;

import app.store.dto.request.ProductImageRequest;
import app.store.dto.response.ProductImageResponse;
import app.store.entity.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    @Mapping(target = "productId", source = "product.id")
    ProductImageResponse toProductImageResponse(ProductImage productImage);

    @Mapping(target = "product", ignore = true)
    ProductImage toProductImage(ProductImageRequest request);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "id", ignore = true)
    ProductImage updateProductImage(@MappingTarget ProductImage productImage, ProductImageRequest request);

}
