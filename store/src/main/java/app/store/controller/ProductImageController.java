package app.store.controller;

import app.store.dto.request.ProductImageRequest;
import app.store.dto.request.ProductImageUpdateRequest;
import app.store.dto.response.ProductImageResponse;
import app.store.dto.response.auth.ApiResponse;
import app.store.service.implementation.ProductImageServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ApiResponse<ProductImageResponse> updateProductImage(@PathVariable Long id, @RequestBody ProductImageUpdateRequest request) {
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
    @PostMapping("/products/{productId}/images/{imageId}/set-primary")
    public ApiResponse<Void> setPrimaryImage(
            @PathVariable Long productId,
            @PathVariable Long imageId) { // Đổi tên biến cho rõ
        productImageServiceImpl.setPrimaryImage(imageId , productId);
        return ApiResponse.<Void>builder()
                .build();
    }
}
