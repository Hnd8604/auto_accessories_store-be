package app.store.mapper;

import app.store.dto.request.OrderDetailRequest;
import app.store.dto.response.OrderDetailResponse;
import app.store.entity.CartItem;
import app.store.entity.Order;
import app.store.entity.OrderDetail;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {


    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "productId", source = "product.id")
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);

    List<OrderDetailResponse> toOrderDetailResponseList(List<OrderDetail> orderDetails);
//
//    @Mapping(target = "product", ignore = true)
//    @Mapping(target = "order", ignore = true)
//    OrderDetail toOrderDetail(OrderDetailRequest request);

}
