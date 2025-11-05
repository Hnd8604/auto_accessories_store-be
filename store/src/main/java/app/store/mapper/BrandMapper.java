package app.store.mapper;

import app.store.dto.request.BrandRequest;
import app.store.dto.response.BrandResponse;
import app.store.dto.response.BrandResponse;
import app.store.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",uses = {ProductMapper.class})
public interface BrandMapper {

    @Mapping(target = "products", source = "products")
    BrandResponse toBrandResponse(Brand brand);

    @Mapping(target = "products", ignore = true)
    Brand toBrand(BrandRequest brandRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateBrand(@MappingTarget Brand brand, BrandRequest brandRequest);
}
