package app.store.mapper;

import app.store.dto.request.OrderDetailRequest;
import app.store.dto.response.OrderDetailResponse;
import app.store.entity.Order;
import app.store.entity.OrderDetail;
import app.store.entity.Product;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-03T15:17:09+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class OrderDetailMapperImpl implements OrderDetailMapper {

    @Override
    public OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }

        OrderDetailResponse.OrderDetailResponseBuilder orderDetailResponse = OrderDetailResponse.builder();

        orderDetailResponse.orderId( orderDetailOrderId( orderDetail ) );
        Long id1 = orderDetailProductId( orderDetail );
        if ( id1 != null ) {
            orderDetailResponse.productId( String.valueOf( id1 ) );
        }
        orderDetailResponse.unitPrice( orderDetailProductUnitPrice( orderDetail ) );
        if ( orderDetail.getId() != null ) {
            orderDetailResponse.id( String.valueOf( orderDetail.getId() ) );
        }
        orderDetailResponse.quantity( orderDetail.getQuantity() );

        return orderDetailResponse.build();
    }

    @Override
    public OrderDetail toOrderDetail(OrderDetailRequest request) {
        if ( request == null ) {
            return null;
        }

        OrderDetail orderDetail = new OrderDetail();

        orderDetail.setQuantity( request.getQuantity() );

        return orderDetail;
    }

    private String orderDetailOrderId(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }
        Order order = orderDetail.getOrder();
        if ( order == null ) {
            return null;
        }
        String id = order.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long orderDetailProductId(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }
        Product product = orderDetail.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private BigDecimal orderDetailProductUnitPrice(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }
        Product product = orderDetail.getProduct();
        if ( product == null ) {
            return null;
        }
        BigDecimal unitPrice = product.getUnitPrice();
        if ( unitPrice == null ) {
            return null;
        }
        return unitPrice;
    }
}
