package app.store.controller;

import app.store.dto.request.ProductImageRequest;
import app.store.dto.response.ProductImageResponse;
import app.store.dto.response.auth.ApiResponse;
import app.store.service.implementation.ProductImageServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-images")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductImageController {
    ProductImageServiceImpl productImageServiceImpl;

    @GetMapping
    public ApiResponse<List<ProductImageResponse>> getAllProductImages() {
        return ApiResponse.<List<ProductImageResponse>>builder()
                .result(productImageServiceImpl.getAllProductImages())
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<ProductImageResponse> getProductImageById(@PathVariable Long id) {

        return ApiResponse.<ProductImageResponse>builder()
                .result(productImageServiceImpl.getProductImageById(id))
                .build();
    }
    @GetMapping("/products/{productId}")
    public ApiResponse<List<ProductImageResponse>> getProductImagesByProductId(@PathVariable Long productId) {
        return ApiResponse.<List<ProductImageResponse>>builder()
                .result(productImageServiceImpl.getProductImagesByProductId(productId))
                .build();
    }

    @PostMapping
    public ApiResponse<ProductImageResponse> createProductImage(@RequestBody ProductImageRequest request) {
        return ApiResponse.<ProductImageResponse>builder()
                .result(productImageServiceImpl.createProductImage(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductImageResponse> updateProductImage(@PathVariable Long id, @RequestBody ProductImageRequest request) {
        return ApiResponse.<ProductImageResponse>builder()
                .result(productImageServiceImpl.updateProductImage(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProductImage(@PathVariable Long id) {
        productImageServiceImpl.deleteProductImage(id);
        return ApiResponse.<Void>builder()
                .build();
    }
}
