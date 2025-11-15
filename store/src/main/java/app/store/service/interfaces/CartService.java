package app.store.service.interfaces;

import app.store.dto.request.CartItemRequest;
import app.store.dto.request.CartItemUpdateRequest;
import app.store.dto.request.CartRequest;
import app.store.dto.response.CartCreationResponse;
import app.store.dto.response.CartItemResponse;
import app.store.dto.response.CartResponse;
import app.store.entity.Cart;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface CartService {


    CartResponse getCartById(Long cartId) throws ParseException, JOSEException;
    CartItemResponse addItemToCart(CartItemRequest request);
    void removeItemFromCart(Long cartId, Long itemId);
    CartItemResponse updateItemInCart(Long itemId, CartItemUpdateRequest request);
}
