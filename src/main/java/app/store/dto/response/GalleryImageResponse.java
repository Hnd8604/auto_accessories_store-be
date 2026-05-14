package app.store.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GalleryImageResponse {

    Long id;
    String imageUrl;
    String caption;
    Integer sortOrder;
}
