package app.store.mapper;

import app.store.dto.request.OrderCreationRequest;
import app.store.dto.response.OrderResponse;
import app.store.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import app.store.mapper.OrderDetailMapper;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {OrderDetailMapper.class})
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "totalPrice", expression = "java(calculateTotalPrice(order))")
    @Mapping(target = "orderDetails", source = "orderDetails")
    OrderResponse toOrderResponse(Order order);

    default BigDecimal calculateTotalPrice(Order order) {
        if(order.getOrderDetails() == null) return BigDecimal.ZERO;
         return order.getOrderDetails().stream()
                .map(orderDetail -> orderDetail.getProduct().getUnitPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    @Mapping(target="user", ignore = true)
    @Mapping(target="status", constant = "PENDING")
    @Mapping(target="paymentStatus", constant = "UNPAID")
    @Mapping(target = "orderDetails", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    Order createOrder(OrderCreationRequest request);

//    @Mapping(target="user", ignore = true)
//    @Mapping(target = "orderDetails", ignore = true)
//    @Mapping(target = "totalPrice", ignore = true)
//    Order updateOrder(OrderUpdateRequest request);
}
