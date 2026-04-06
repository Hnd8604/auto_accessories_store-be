package app.store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "gallery_categories")
public class GalleryCategory extends BaseEntityLong {

    @Column(nullable = false, length = 255)
    String name;

    @Column(unique = true, nullable = false, length = 255)
    String slug;

    @Column(length = 1000)
    String description;

    @Column
    @Builder.Default
    Integer sortOrder = 0;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<GalleryItem> items;
}
