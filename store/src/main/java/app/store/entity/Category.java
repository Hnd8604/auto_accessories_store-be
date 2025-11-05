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
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category extends BaseEntityLong {
    String name;
    @Column(columnDefinition = "TEXT")
    String description;
    @OneToMany(mappedBy ="category", cascade = CascadeType.ALL)
    List<Product> products;
}
