package app.store.mapper;

import app.store.dto.response.OrderDetailResponse;
import app.store.entity.Order;
import app.store.entity.OrderDetail;
import app.store.entity.Product;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-18T15:00:11+0700",
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
        if ( orderDetail.getId() != null ) {
            orderDetailResponse.id( String.valueOf( orderDetail.getId() ) );
        }
        orderDetailResponse.quantity( orderDetail.getQuantity() );

        return orderDetailResponse.build();
    }

    @Override
    public List<OrderDetailResponse> toOrderDetailResponseList(List<OrderDetail> orderDetails) {
        if ( orderDetails == null ) {
            return null;
        }

        List<OrderDetailResponse> list = new ArrayList<OrderDetailResponse>( orderDetails.size() );
        for ( OrderDetail orderDetail : orderDetails ) {
            list.add( toOrderDetailResponse( orderDetail ) );
        }

        return list;
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
}
