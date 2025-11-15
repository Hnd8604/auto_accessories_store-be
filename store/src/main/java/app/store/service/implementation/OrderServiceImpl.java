package app.store.service.implementation;

import app.store.dto.request.OrderCreationRequest;

import app.store.dto.request.OrderUpdateByAdminRequest;
import app.store.dto.request.OrderUpdateByUserRequest;
import app.store.dto.response.OrderResponse;
import app.store.entity.*;
import app.store.enums.OrderStatus;
import app.store.mapper.CartMapper;
import app.store.mapper.OrderMapper;
import app.store.repository.CartRepository;
import app.store.repository.OrderRepository;
import app.store.repository.UserRepository;
import app.store.service.interfaces.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    UserRepository userRepository;
    CartItemServiceImpl cartItemServiceImpl;
    CartRepository cartRepository;
    CartMapper cartMapper;

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toOrderResponse).toList();
    }

    @Override
    public OrderResponse getOrderById(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getMyOrder() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        List<Order> orders = orderRepository.getOrderByUserName(name);
        return orders.stream()
                .map(orderMapper::toOrderResponse).toList();
    }

    @Override
    public OrderResponse createOrderFromCart(OrderCreationRequest orderRequest) {
        Order order = orderMapper.createOrder(orderRequest);
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // lấy cartId theo UserId, lấy cartItem theo cartId
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));
        List<OrderDetail> orderDetails = new ArrayList<>();

        for( CartItem item : cart.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(item.getProduct());
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setOrder(order);
            orderDetail.setUnitPrice(item.getProduct().getUnitPrice());
            orderDetails.add(orderDetail);
        }
        order.setOrderDetails(orderDetails);
        order.setTotalPrice(cartMapper.toCartResponse(cart).getTotalPrice());
        order.setUser(user);

        cart.getCartItems().clear();
        cartRepository.save(cart);
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse updateOrderByAdmin(String orderId, OrderUpdateByAdminRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(request.getOrderStatus());
        order.setPaymentStatus(request.getPaymentStatus());
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse updateOrderByUser(String orderId, OrderUpdateByUserRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setNameRecipient(request.getNameRecipient());
        order.setAddressRecipient(request.getAddressRecipient());
        order.setPhoneRecipient(request.getPhoneRecipient());
        order.setNote(request.getNote());
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }
    @Override
    public OrderResponse cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (order.getStatus() == OrderStatus.PENDING || order.getStatus() == OrderStatus.PROCESSING) {
            order.setStatus(OrderStatus.CANCELED);
            return orderMapper.toOrderResponse(orderRepository.save(order));
        }
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public void deleteOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.delete(order);

    }
}
