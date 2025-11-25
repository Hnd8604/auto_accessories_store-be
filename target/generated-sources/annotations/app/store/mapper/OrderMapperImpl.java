package app.store.mapper;

import app.store.dto.request.OrderCreationRequest;
import app.store.dto.response.OrderResponse;
import app.store.entity.Order;
import app.store.entity.User;
import app.store.enums.OrderStatus;
import app.store.enums.PaymentStatus;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-24T21:26:59+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public OrderResponse toOrderResponse(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderResponse.OrderResponseBuilder orderResponse = OrderResponse.builder();

        orderResponse.userId( orderUserId( order ) );
        orderResponse.orderDetails( orderDetailMapper.toOrderDetailResponseList( order.getOrderDetails() ) );
        orderResponse.id( order.getId() );
        orderResponse.totalPrice( order.getTotalPrice() );
        orderResponse.nameRecipient( order.getNameRecipient() );
        orderResponse.phoneRecipient( order.getPhoneRecipient() );
        orderResponse.addressRecipient( order.getAddressRecipient() );
        orderResponse.note( order.getNote() );
        orderResponse.status( order.getStatus() );
        orderResponse.paymentStatus( order.getPaymentStatus() );

        return orderResponse.build();
    }

    @Override
    public Order createOrder(OrderCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        Order order = new Order();

        order.setNameRecipient( request.getNameRecipient() );
        order.setPhoneRecipient( request.getPhoneRecipient() );
        order.setAddressRecipient( request.getAddressRecipient() );
        order.setNote( request.getNote() );

        order.setStatus( OrderStatus.PENDING );
        order.setPaymentStatus( PaymentStatus.UNPAID );

        return order;
    }

    private String orderUserId(Order order) {
        if ( order == null ) {
            return null;
        }
        User user = order.getUser();
        if ( user == null ) {
            return null;
        }
        String id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
