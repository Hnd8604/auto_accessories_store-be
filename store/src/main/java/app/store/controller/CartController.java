package app.store.controller;

import app.store.dto.request.CartItemRequest;
import app.store.dto.request.CartItemUpdateRequest;
import app.store.dto.request.CartRequest;
import app.store.dto.response.CartCreationResponse;
import app.store.dto.response.CartResponse;
import app.store.dto.response.auth.ApiResponse;
import app.store.dto.response.auth.CartItemResponse;
import app.store.service.implementation.CartItemServiceImpl;
import app.store.service.implementation.CartServiceImpl;

import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    CartServiceImpl cartServiceImpl;
    @PostMapping
    ApiResponse<CartCreationResponse> createCart(@RequestBody CartRequest request) throws ParseException, JOSEException {
        ApiResponse<CartCreationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartServiceImpl.createCart(request));
        return apiResponse;
    }

    @GetMapping("/{cartId}")
    ApiResponse<CartResponse> getCartById(@PathVariable Long cartId) throws ParseException, JOSEException {
        ApiResponse<CartResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartServiceImpl.getCartById(cartId));
        return apiResponse;
    }

// cartItem
    CartItemServiceImpl cartItemServiceImpl;
    @PostMapping("/items")
    ApiResponse<CartItemResponse> addItemToCart(@RequestBody CartItemRequest cartItemRequest) {
        ApiResponse<CartItemResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartItemServiceImpl.addItemToCart(cartItemRequest));
        return apiResponse;
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    ApiResponse<Void> removeItemFromCart(@PathVariable  Long cartId, @PathVariable Long itemId) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        cartItemServiceImpl.removeItemFromCart(cartId, itemId);
        return apiResponse;
    }
    @PutMapping("/items/{itemId}")
    ApiResponse<CartItemResponse> updateItemInCart(@PathVariable Long itemId, @RequestBody CartItemUpdateRequest request) {
        ApiResponse<CartItemResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartItemServiceImpl.updateItemInCart(itemId, request));
        return apiResponse;
    }

    @GetMapping("/{cartId}/items")
    ApiResponse<List<CartItemResponse>> getAllItemsInCart(@PathVariable Long cartId) {
        ApiResponse<List<CartItemResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cartItemServiceImpl.getAllItemsInCart(cartId));
        return apiResponse;
    }
}
