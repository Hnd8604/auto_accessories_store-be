package app.store.dto.response.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CartItemResponse {
    Long id;
    Long cartId;
    Long productId;
    String productName;
    int quantity;
    BigDecimal pricePerUnit;
    BigDecimal totalPrice;
}
