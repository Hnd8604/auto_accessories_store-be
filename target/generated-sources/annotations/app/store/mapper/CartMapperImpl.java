package app.store.mapper;

import app.store.dto.request.CartRequest;
import app.store.dto.response.CartCreationResponse;
import app.store.dto.response.CartResponse;
import app.store.dto.response.auth.CartItemResponse;
import app.store.entity.Cart;
import app.store.entity.CartItem;
import app.store.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-30T07:56:34+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Autowired
    private CartItemMapper cartItemMapper;

    @Override
    public CartResponse toCartResponse(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartResponse.CartResponseBuilder cartResponse = CartResponse.builder();

        cartResponse.userId( cartUserId( cart ) );
        cartResponse.items( cartItemListToCartItemResponseList( cart.getCartItems() ) );

        cartResponse.totalItems( cart.getCartItems() != null ? cart.getCartItems().size() : 0 );
        cartResponse.totalPrice( calculateTotalPrice(cart) );

        return cartResponse.build();
    }

    @Override
    public Cart toCart(CartRequest request) {
        if ( request == null ) {
            return null;
        }

        Cart.CartBuilder cart = Cart.builder();

        return cart.build();
    }

    @Override
    public CartCreationResponse toCartCreationResponse(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartCreationResponse.CartCreationResponseBuilder cartCreationResponse = CartCreationResponse.builder();

        cartCreationResponse.userId( cartUserId( cart ) );

        return cartCreationResponse.build();
    }

    private String cartUserId(Cart cart) {
        if ( cart == null ) {
            return null;
        }
        User user = cart.getUser();
        if ( user == null ) {
            return null;
        }
        String id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected List<CartItemResponse> cartItemListToCartItemResponseList(List<CartItem> list) {
        if ( list == null ) {
            return null;
        }

        List<CartItemResponse> list1 = new ArrayList<CartItemResponse>( list.size() );
        for ( CartItem cartItem : list ) {
            list1.add( cartItemMapper.toCartItemResponse( cartItem ) );
        }

        return list1;
    }
}
