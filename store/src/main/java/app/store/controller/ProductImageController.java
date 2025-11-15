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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping("/{imageId}")
    public ApiResponse<ProductImageResponse> getProductImageById(@PathVariable Long imageId) {

        return ApiResponse.<ProductImageResponse>builder()
                .result(productImageServiceImpl.getProductImageById(imageId))
                .build();
    }
    @GetMapping("/products/{productId}")
    public ApiResponse<List<ProductImageResponse>> getProductImagesByProductId(@PathVariable Long productId) {
        return ApiResponse.<List<ProductImageResponse>>builder()
                .result(productImageServiceImpl.getProductImagesByProductId(productId))
                .build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ProductImageResponse> createProductImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("productId") Long productId,
            @RequestParam(value = "isPrimary", defaultValue = "false") Boolean isPrimary) {
        ProductImageRequest request = ProductImageRequest.builder()
                .productId(productId)
                .isPrimary(isPrimary)
                .build();
        return ApiResponse.<ProductImageResponse>builder()
                .result(productImageServiceImpl.createProductImage(file, request))
                .build();
    }

    @PutMapping("/{imageId}")
    public ApiResponse<ProductImageResponse> updateProductImage(@PathVariable Long imageId, @RequestBody ProductImageUpdateRequest request) {
        return ApiResponse.<ProductImageResponse>builder()
                .result(productImageServiceImpl.updateProductImage(imageId, request))
                .build();
    }

    @DeleteMapping("/{imageId}")
    public ApiResponse<Void> deleteProductImage(@PathVariable Long imageId) {
        productImageServiceImpl.deleteProductImage(imageId);
        return ApiResponse.<Void>builder()
                .message("Product image deleted successfully")
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
