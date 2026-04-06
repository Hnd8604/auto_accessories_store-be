package app.store.mapper;

import app.store.dto.request.GalleryItemRequest;
import app.store.dto.response.GalleryItemResponse;
import app.store.entity.GalleryItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {GalleryImageMapper.class})
public interface GalleryItemMapper {

    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "thumbnailUrl", ignore = true)
    GalleryItem toEntity(GalleryItemRequest request);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "images", source = "images")
    GalleryItemResponse toResponse(GalleryItem entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "thumbnailUrl", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(GalleryItemRequest request, @MappingTarget GalleryItem entity);
}
