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
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "unitPrice", source = "product.unitPrice")
    @Mapping(target = "totalPrice", expression = "java(calculateTotal(item))")
    CartItemResponse toCartItemResponse(CartItem item);

    default BigDecimal calculateTotal(CartItem item) {
        if (item.getProduct() == null || item.getQuantity() == 0) return BigDecimal.ZERO;
        return item.getProduct().getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
    }


    @Mapping(target = "product", ignore = true)
    @Mapping(target = "cart", ignore = true)
    CartItem toCartItem(CartItemRequest request);

}