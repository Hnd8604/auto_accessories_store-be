package app.store.service.interfaces;

import app.store.dto.request.OrderCreationRequest;
import app.store.dto.request.OrderUpdateByAdminRequest;
import app.store.dto.request.OrderUpdateByUserRequest;
import app.store.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getAllOrders(); // admin
    OrderResponse getOrderById(String orderId); // admin, user
    List<OrderResponse> getMyOrders(); // user
    OrderResponse createOrderFromCart(OrderCreationRequest orderRequest);
    OrderResponse updateOrderByAdmin(String orderId, OrderUpdateByAdminRequest request); // admin
    OrderResponse updateOrderByUser(String orderId, OrderUpdateByUserRequest request);
    OrderResponse cancelOrder(String orderId); // user
    OrderResponse deleteOrder(String orderId); // admin
}
