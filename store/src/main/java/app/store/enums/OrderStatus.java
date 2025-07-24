package app.store.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    PENDING("Chờ xác nhận"),
    CONFIRMED("Đã xác nhận"),
    SHIPPED("Đang giao hàng"),
    DELIVERED("Đã giao hàng"),
    CANCELLED("Đã hủy");

    private final String description;
}