package app.store.mapper;


import app.store.dto.request.CartRequest;
import app.store.dto.response.CartCreationResponse;
import app.store.dto.response.CartResponse;
import app.store.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "items", source = "cartItems")
    @Mapping(target = "totalItems", expression = "java(cart.getCartItems() != null ? cart.getCartItems().size() : 0)")
    @Mapping(target = "totalPrice", expression = "java(calculateTotalPrice(cart))")
    CartResponse toCartResponse(Cart cart);


    default BigDecimal calculateTotalPrice(Cart cart) {
        if (cart.getCartItems() == null) return BigDecimal.ZERO;

        return cart.getCartItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Mapping(target = "cartItems", ignore = true)
    @Mapping(target = "user", ignore = true)
    Cart toCart(CartRequest request);

    @Mapping(target = "userId", source = "user.id")
    CartCreationResponse toCartCreationResponse(Cart cart);
}
