package app.store.mapper;

import app.store.dto.response.CartItemResponse;
import app.store.entity.Cart;
import app.store.entity.CartItem;
import app.store.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-24T21:26:59+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class CartItemMapperImpl implements CartItemMapper {

    @Override
    public CartItemResponse toCartItemResponse(CartItem item) {
        if ( item == null ) {
            return null;
        }

        CartItemResponse.CartItemResponseBuilder cartItemResponse = CartItemResponse.builder();

        cartItemResponse.cartId( itemCartId( item ) );
        cartItemResponse.productId( itemProductId( item ) );
        cartItemResponse.id( item.getId() );
        cartItemResponse.quantity( item.getQuantity() );

        return cartItemResponse.build();
    }

    private Long itemCartId(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }
        Cart cart = cartItem.getCart();
        if ( cart == null ) {
            return null;
        }
        Long id = cart.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long itemProductId(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }
        Product product = cartItem.getProduct();
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
