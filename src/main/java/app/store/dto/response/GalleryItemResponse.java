package app.store.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GalleryItemResponse {

    Long id;
    String title;
    String slug;
    String description;
    String thumbnailUrl;
    String carModel;
    Boolean published;
    Long viewCount;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    // Category info
    Long categoryId;
    String categoryName;

    // Images
    List<GalleryImageResponse> images;
}
