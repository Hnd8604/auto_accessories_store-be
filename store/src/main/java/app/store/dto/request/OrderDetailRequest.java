package app.store.dto.request;

import app.store.enums.OrderStatus;
import app.store.enums.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequest {
    String productId;
    String orderId;
    Integer quantity;

}
