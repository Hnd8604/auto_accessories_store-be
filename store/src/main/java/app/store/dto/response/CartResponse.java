package app.store.dto.response;

import app.store.dto.response.auth.CartItemResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    BigDecimal totalPrice;
    int totalItems;
    //BigDecimal price; add after has voucher
    String userId;
    List<CartItemResponse> items;
}