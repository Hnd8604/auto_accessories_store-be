package app.store.controller;

import app.store.dto.request.ProductCreationRequest;
import app.store.dto.request.ProductUpdateRequest;
import app.store.dto.response.ProductResponse;
import app.store.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @PostMapping
    ProductResponse createProduct(@RequestBody ProductCreationRequest request) {
        return productService.createProduct(request);
    }

    @PutMapping("/{productId}")
    ProductResponse updateProduct(@PathVariable String productId, @RequestBody ProductUpdateRequest request) {
        return productService.updateProduct(productId, request);
    }

    @DeleteMapping("/{productId}")
    ProductResponse deleteProduct(@PathVariable String productId) {
        return productService.deleteProduct(productId);
    }

    @GetMapping
    List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }


    @GetMapping("/{productId}")
    ProductResponse getProduct(@PathVariable String productId) {
        return productService.getProduct(productId);
    }
}
