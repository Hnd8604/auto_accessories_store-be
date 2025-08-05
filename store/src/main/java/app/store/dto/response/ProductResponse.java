package app.store.dto.response;


import app.store.entity.Branch;
import app.store.entity.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long id;
    String name;
    String description;
    BigDecimal unitPrice;
    String categoryName;
    String branchName;
    Integer stockQuantity;
}
