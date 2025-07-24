package app.store.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long productId;
    String productName;
    String description;
    BigDecimal price;

    @ManyToOne
    Category category;
    int stockQuantity;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
