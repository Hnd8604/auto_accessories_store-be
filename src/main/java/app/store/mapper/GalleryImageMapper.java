package app.store.mapper;

import app.store.dto.response.GalleryImageResponse;
import app.store.entity.GalleryImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GalleryImageMapper {

    GalleryImageResponse toResponse(GalleryImage entity);
}
