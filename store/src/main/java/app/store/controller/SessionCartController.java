package app.store.controller;

import app.store.exception.AppException;
import app.store.exception.ErrorCode;
import app.store.repository.ProductRepository;
import app.store.service.impl.SessionCartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("session-carts")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionCartController {

    SessionCartService sessionCartService;
    ProductRepository productRepository;
    @PostMapping("/add")
    public Map<Long, Integer> add(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int qty
    ) {
        // Check product exists
        productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        return sessionCartService.addToCart(productId, qty);
    }

    @GetMapping
    public Map<Long, Integer> view() {
        return sessionCartService.getSessionCart();
    }

    @DeleteMapping("/remove/{productId}")
    public Map<Long, Integer> remove(@PathVariable Long productId) {
        return sessionCartService.removeFromCart(productId);
    }
}
