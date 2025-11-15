package app.store.mapper;


import app.store.dto.request.CartItemRequest;
import app.store.dto.response.CartItemResponse;
import app.store.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CartItemMapper {


    @Mapping(target = "cartId", source = "cart.id")
    @Mapping(target = "productId", source = "product.id")
    CartItemResponse toCartItemResponse(CartItem item);


//    @Mapping(target = "product", ignore = true)
//    @Mapping(target = "cart", ignore = true)
//    CartItem toCartItem(CartItemRequest request);

}