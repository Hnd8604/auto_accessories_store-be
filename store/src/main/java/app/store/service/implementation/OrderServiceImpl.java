package app.store.service.implementation;

import app.store.dto.request.OrderCreationRequest;

import app.store.dto.request.OrderDetailRequest;
import app.store.dto.request.OrderUpdateByAdminRequest;
import app.store.dto.request.OrderUpdateByUserRequest;
import app.store.dto.response.OrderResponse;
import app.store.entity.*;
import app.store.enums.OrderStatus;
import app.store.exception.AppException;
import app.store.exception.ErrorCode;
import app.store.mapper.CartMapper;
import app.store.mapper.OrderMapper;
import app.store.repository.*;
import app.store.service.interfaces.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    CartRepository cartRepository;
    ProductRepository productRepository;
    CartItemRepository cartItemRepository;

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toOrderResponse).toList();
    }

    @Override
    public OrderResponse getOrderById(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
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
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // lấy cartId theo UserId, lấy cartItem theo cartId
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));
        List<OrderDetail> orderDetails = new ArrayList<>();

        BigDecimal totalPrice = new BigDecimal(0);
        for( OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetails()) { // tao tung order detail tu request
            Product product = productRepository.findById(orderDetailRequest.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

            if(orderDetailRequest.getQuantity() <= 0 || orderDetailRequest.getQuantity() > product.getStockQuantity()) {
                throw new IllegalArgumentException("Quantity is not valid for product: " + product.getName());
            }
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(product);
            orderDetail.setQuantity(orderDetailRequest.getQuantity());
            orderDetail.setOrder(order);
            orderDetails.add(orderDetail);


            // cong tien
            totalPrice = totalPrice.add(product.getUnitPrice().multiply(BigDecimal.valueOf(orderDetailRequest.getQuantity())));

            // giam so luong trong gio hang
            // tu cart->cartItem-> product -> giam so luong product trong cartItem, neu = 0 thi xoa luon cartItem di
            CartItem cartItem = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(product.getId()))
                    .findFirst()
                    .orElse(null);
            if(cartItem != null) {
                cartItem.setQuantity(cartItem.getQuantity() - orderDetailRequest.getQuantity());

                if (cartItem.getQuantity() == 0) {
                    cart.getCartItems().remove(cartItem);
                    cartItemRepository.delete(cartItem);
                }
            }
            else {
                throw new AppException(ErrorCode.CART_ITEM_NOT_EXISTED);
            }

        // giam so luong product
            product.setStockQuantity(product.getStockQuantity() - orderDetailRequest.getQuantity());
        }
        order.setOrderDetails(orderDetails);
        order.setTotalPrice(totalPrice);
        order.setUser(user);
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse updateOrderByAdmin(String orderId, OrderUpdateByAdminRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
        order.setStatus(request.getOrderStatus());
        order.setPaymentStatus(request.getPaymentStatus());
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse updateOrderByUser(String orderId, OrderUpdateByUserRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
        order.setNameRecipient(request.getNameRecipient());
        order.setAddressRecipient(request.getAddressRecipient());
        order.setPhoneRecipient(request.getPhoneRecipient());
        order.setNote(request.getNote());
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }
    @Override
    public OrderResponse cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
        if (order.getStatus() == OrderStatus.PENDING || order.getStatus() == OrderStatus.PROCESSING) {
            order.setStatus(OrderStatus.CANCELED);

        }
        else {
            throw new RuntimeException("Only orders with status PENDING or PROCESSING can be canceled");
        }
        List<OrderDetail> orderDetails = order.getOrderDetails();
        for( OrderDetail orderDetail : orderDetails ) {
            Product product = orderDetail.getProduct();
            product.setStockQuantity(product.getStockQuantity() + orderDetail.getQuantity());
        }
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
        orderRepository.delete(order);
    }
}
