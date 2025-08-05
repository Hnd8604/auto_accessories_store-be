package app.store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product extends BaseEntityLong {

    String name;
    String description;
    BigDecimal unitPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;
    Integer stockQuantity;
//    @ManyToOne
//    Branch branch;

    @OneToMany(mappedBy ="product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductImage> productImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "branch_id")
    Branch branch;
}
