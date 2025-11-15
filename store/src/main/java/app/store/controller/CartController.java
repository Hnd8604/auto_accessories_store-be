package app.store.controller;

import app.store.dto.request.CartItemRequest;
import app.store.dto.request.CartItemUpdateRequest;
import app.store.dto.request.CartRequest;
import app.store.dto.response.BrandResponse;
import app.store.dto.response.CartCreationResponse;
import app.store.dto.response.CartResponse;
import app.store.dto.response.auth.ApiResponse;
import app.store.dto.response.CartItemResponse;
import app.store.service.implementation.CartItemServiceImpl;
import app.store.service.implementation.CartServiceImpl;

import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    CartServiceImpl cartServiceImpl;

    @GetMapping("/{cartId}")
    ApiResponse<CartResponse> getCartById(@PathVariable Long cartId) throws ParseException, JOSEException {
        return ApiResponse.<CartResponse>builder()
                .result(cartServiceImpl.getCartById(cartId))
                .build();
    }

// cartItem
    CartItemServiceImpl cartItemServiceImpl;
    @PostMapping("/items")
    ApiResponse<CartItemResponse> addItemToCart(@RequestBody CartItemRequest cartItemRequest) {
        return ApiResponse.<CartItemResponse>builder()
                .result(cartItemServiceImpl.addItemToCart(cartItemRequest))
                .build();
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    ApiResponse<Void> removeItemFromCart(@PathVariable  Long cartId, @PathVariable Long itemId) {
        cartItemServiceImpl.removeItemFromCart(cartId, itemId);
        return ApiResponse.<Void>builder().build();
    }
    @PutMapping("/items/{itemId}")
    ApiResponse<CartItemResponse> updateItemInCart(@PathVariable Long itemId, @RequestBody CartItemUpdateRequest request) {
        return ApiResponse.<CartItemResponse>builder()
                .result(cartItemServiceImpl.updateItemInCart(itemId, request))
                .build();
    }

    @GetMapping("/{cartId}/items")
    ApiResponse<List<CartItemResponse>> getAllItemsInCart(@PathVariable Long cartId) {
    return ApiResponse.<List<CartItemResponse>>builder()
                .result(cartItemServiceImpl.getAllItemsInCart(cartId))
                .build();
    }
}
