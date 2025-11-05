package app.store.mapper;

import app.store.dto.request.CartItemRequest;
import app.store.dto.response.CartItemResponse;
import app.store.entity.Cart;
import app.store.entity.CartItem;
import app.store.entity.Product;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-05T08:19:40+0700",
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
        cartItemResponse.productName( itemProductName( item ) );
        cartItemResponse.unitPrice( itemProductUnitPrice( item ) );
        cartItemResponse.id( item.getId() );
        cartItemResponse.quantity( item.getQuantity() );

        cartItemResponse.totalPrice( calculateTotal(item) );

        return cartItemResponse.build();
    }

    @Override
    public CartItem toCartItem(CartItemRequest request) {
        if ( request == null ) {
            return null;
        }

        CartItem.CartItemBuilder cartItem = CartItem.builder();

        cartItem.quantity( request.getQuantity() );

        return cartItem.build();
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

    private String itemProductName(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }
        Product product = cartItem.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private BigDecimal itemProductUnitPrice(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }
        Product product = cartItem.getProduct();
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
