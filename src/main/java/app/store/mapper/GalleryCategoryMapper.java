package app.store.mapper;

import app.store.dto.request.GalleryCategoryRequest;
import app.store.dto.response.GalleryCategoryResponse;
import app.store.entity.GalleryCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GalleryCategoryMapper {

    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "items", ignore = true)
    GalleryCategory toEntity(GalleryCategoryRequest request);

    @Mapping(target = "itemCount", expression = "java(entity.getItems() != null ? (long) entity.getItems().size() : 0L)")
    GalleryCategoryResponse toResponse(GalleryCategory entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(GalleryCategoryRequest request, @MappingTarget GalleryCategory entity);
}
