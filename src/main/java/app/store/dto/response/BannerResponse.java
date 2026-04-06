package app.store.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerResponse {
    Long id;
    String title;
    String imageUrl;
    String redirectUrl;
    String altText;
    Integer displayOrder;
    Boolean isActive;
}
