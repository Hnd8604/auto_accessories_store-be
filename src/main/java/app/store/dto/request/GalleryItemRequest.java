package app.store.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GalleryItemRequest {

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 500, message = "Tiêu đề không được vượt quá 500 ký tự")
    String title;

    @Size(max = 2000, message = "Mô tả không được vượt quá 2000 ký tự")
    String description;

    @Size(max = 255, message = "Tên xe không được vượt quá 255 ký tự")
    String carModel;

    Boolean published;

    Long categoryId;
}
