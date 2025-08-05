package app.store.service.interfaces;

import app.store.dto.request.CartItemRequest;
import app.store.dto.request.CartItemUpdateRequest;
import app.store.dto.response.CartItemResponse;

import java.util.List;

public interface CartItemService {

    CartItemResponse addItemToCart(CartItemRequest request);
    void removeItemFromCart(Long cartId, Long itemId);

    CartItemResponse updateItemInCart(Long itemId, CartItemUpdateRequest request);
    List<CartItemResponse> getAllItemsInCart(Long cartId);

}
