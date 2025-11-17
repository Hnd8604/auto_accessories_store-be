package app.store.service.interfaces;

import app.store.dto.request.OrderCreationRequest;
import app.store.dto.request.OrderUpdateByAdminRequest;
import app.store.dto.request.OrderUpdateByUserRequest;
import app.store.dto.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Page<OrderResponse> getAllOrders(Pageable pageable); // admin
    OrderResponse getOrderById(String orderId); // admin, user
    List<OrderResponse> getMyOrder(); // user
    OrderResponse createOrderFromCart(OrderCreationRequest orderRequest);
    OrderResponse updateOrderByAdmin(String orderId, OrderUpdateByAdminRequest request); // admin
    OrderResponse updateOrderByUser(String orderId, OrderUpdateByUserRequest request);
    OrderResponse cancelOrder(String orderId); // user
    void deleteOrder(String orderId); // admin
}
