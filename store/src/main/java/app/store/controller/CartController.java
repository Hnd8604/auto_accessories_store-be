package app.store.controller;

import app.store.constant.ResponseMessage;
import app.store.dto.request.CartItemRequest;
import app.store.dto.request.CartItemUpdateRequest;
import app.store.dto.response.CartResponse;
import app.store.dto.response.auth.ApiResponse;
import app.store.dto.response.CartItemResponse;
import app.store.service.impl.CartServiceImpl;

import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

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
                .message(ResponseMessage.GET_CART_SUCCESS)
                .build();
    }

    @PostMapping("/items")
    ApiResponse<CartItemResponse> addItemToCart(@RequestBody CartItemRequest cartItemRequest) {
        return ApiResponse.<CartItemResponse>builder()
                .result(cartServiceImpl.addItemToCart(cartItemRequest))
                .message(ResponseMessage.ADD_ITEM_SUCCESS)
                .build();
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    ApiResponse<Void> removeItemFromCart(@PathVariable  Long cartId, @PathVariable Long itemId) {
        cartServiceImpl.removeItemFromCart(cartId, itemId);
        return ApiResponse.<Void>builder().
                message(ResponseMessage.REMOVE_ITEM_SUCCESS).build();

    }
    @PutMapping("/items/{itemId}")
    ApiResponse<CartItemResponse> updateItemInCart(@PathVariable Long itemId, @RequestBody CartItemUpdateRequest request) {
        return ApiResponse.<CartItemResponse>builder()
                .result(cartServiceImpl.updateItemInCart(itemId, request))
                .message(ResponseMessage.UPDATE_ITEM_SUCCESS)
                .build();
    }
}
