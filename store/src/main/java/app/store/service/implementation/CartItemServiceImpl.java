package app.store.service.implementation;

import app.store.dto.request.CartItemRequest;
import app.store.dto.request.CartItemUpdateRequest;
import app.store.dto.response.CartItemResponse;
import app.store.entity.Cart;
import app.store.entity.CartItem;
import app.store.entity.Product;
import app.store.mapper.CartItemMapper;
import app.store.repository.CartItemRepository;
import app.store.repository.CartRepository;
import app.store.repository.ProductRepository;
import app.store.service.interfaces.CartItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CartItemServiceImpl implements CartItemService {
    CartRepository cartRepository;
    ProductRepository productRepository;
    CartItemMapper cartItemMapper;
    CartItemRepository cartItemRepository;
    @Override
    public CartItemResponse addItemToCart(CartItemRequest request) {
        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);
        return cartItemMapper.toCartItemResponse(cartItem);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long itemId) {
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        if (!item.getCart().getId().equals(cartId)) {
            throw new IllegalArgumentException("Item does not belong to cart " + cartId);
        }
        cartItemRepository.delete(item);
    }




    @Override
    public CartItemResponse updateItemInCart(Long itemId, CartItemUpdateRequest request) {
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);
        return cartItemMapper.toCartItemResponse(cartItem);
    }

    @Override
    public List<CartItemResponse> getAllItemsInCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
        List<CartItem> items = cart.getCartItems(); // cần đảm bảo mối quan hệ 2 chiều đã cấu hình
        return items.stream()
                .map(cartItemMapper::toCartItemResponse)
                .toList();
    }
}
