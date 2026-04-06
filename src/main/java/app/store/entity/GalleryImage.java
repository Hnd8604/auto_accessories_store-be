package app.store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "gallery_images")
public class GalleryImage extends BaseEntityLong {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gallery_item_id")
    GalleryItem galleryItem;

    String imageUrl;

    @Column(length = 500)
    String caption;

    @Column
    @Builder.Default
    Integer sortOrder = 0;
}
