package app.store.controller;

import app.store.dto.request.OrderCreationRequest;
import app.store.dto.request.OrderUpdateByAdminRequest;
import app.store.dto.request.OrderUpdateByUserRequest;
import app.store.dto.response.CategoryResponse;
import app.store.dto.response.OrderResponse;
import app.store.dto.response.auth.ApiResponse;
import app.store.entity.Order;
import app.store.service.implementation.OrderServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderServiceImpl orderServiceImpl;

    @GetMapping
    ApiResponse<List<OrderResponse>> getAllOrders() {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderServiceImpl.getAllOrders())
                .build();
    }

    @GetMapping("/{orderId}")
    ApiResponse<OrderResponse> getOrder(@PathVariable String orderId) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderServiceImpl.getOrderById(orderId))
                .build();
    }

    @GetMapping("/my-orders")
    ApiResponse<List<OrderResponse>> getMyOrders() {
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderServiceImpl.getMyOrders())
                .build();
    }

    @PostMapping
    ApiResponse<OrderResponse> createOrderFromCart(@RequestBody OrderCreationRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderServiceImpl.createOrderFromCart(request))
                .build();
    }

    @PutMapping("/{orderId}/update-by-user")
    ApiResponse<OrderResponse> updateOrderByUser(@PathVariable String orderId, @RequestBody OrderUpdateByUserRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderServiceImpl.updateOrderByUser(orderId, request))
                .build();
    }

    @PutMapping("/{orderId}/update-by-admin")
    ApiResponse<OrderResponse> updateOrderByAdmin(@PathVariable String orderId, @RequestBody OrderUpdateByAdminRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderServiceImpl.updateOrderByAdmin(orderId, request))
                .build();
    }

    @PutMapping("{orderId}/cancel")
    ApiResponse<OrderResponse> cancelOrder(@PathVariable String orderId) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderServiceImpl.cancelOrder(orderId))
                .build();
    }

    @DeleteMapping("/{orderId}")
    ApiResponse<Void> deleteOrder(@PathVariable String orderId) {
        orderServiceImpl.deleteOrder(orderId);
        return ApiResponse.<Void>builder()
                .build();
    }
}
