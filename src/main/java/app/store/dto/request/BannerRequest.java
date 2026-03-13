package app.store.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerRequest {
    String title;
    String redirectUrl;
    String altText;
    Integer displayOrder;
    Boolean isActive;
}
