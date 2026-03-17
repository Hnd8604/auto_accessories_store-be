package app.store.dto.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreatedEvent {
    String orderId;
    String orderCode;
    String userId;
    String userEmail;
    String recipientName;
    BigDecimal totalPrice;
    String paymentMethod;
    LocalDateTime createdAt;
}
