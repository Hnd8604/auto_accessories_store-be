package app.store.service.interfaces;

import app.store.dto.request.CartItemRequest;
import app.store.dto.response.auth.CartItemResponse;
import app.store.entity.CartItem;

import java.util.List;

public interface CartItemService {
    // add Item to cart
    CartItemResponse addItemToCart(CartItemRequest request);
    // remove Item from cart
    void removeItemFromCart();
    // update Item quantity in cart
    CartItemResponse updateItemInCart(CartItemRequest request);
    // get all items in cart
    List<CartItemResponse> getAllItemsInCart();
}
