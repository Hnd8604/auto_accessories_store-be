package app.store.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GalleryCategoryResponse {

    Long id;
    String name;
    String slug;
    String description;
    Integer sortOrder;
    Long itemCount;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
