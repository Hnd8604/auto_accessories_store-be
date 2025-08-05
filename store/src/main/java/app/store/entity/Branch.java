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
public class Branch extends BaseEntityLong{
    String name;
    String description;
    @OneToMany(mappedBy ="branch", cascade = CascadeType.ALL)
    List<Product> products;
}
