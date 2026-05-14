package app.store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "gallery_items")
public class GalleryItem extends BaseEntityLong {

    @Column(nullable = false, length = 500)
    String title;

    @Column(unique = true, nullable = false, length = 500)
    String slug;

    @Column(length = 2000)
    String description;

    String thumbnailUrl;

    @Column(length = 255)
    String carModel;

    @Column(nullable = false)
    @Builder.Default
    Boolean published = false;

    @Column
    @Builder.Default
    Long viewCount = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    GalleryCategory category;

    @OneToMany(mappedBy = "galleryItem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    List<GalleryImage> images = new ArrayList<>();
}
