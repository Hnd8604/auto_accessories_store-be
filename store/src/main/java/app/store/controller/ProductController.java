package app.store.controller;

import app.store.dto.request.ProductCreationRequest;
import app.store.dto.request.ProductUpdateRequest;
import app.store.dto.response.ApiResponse;
import app.store.dto.response.ProductResponse;
import app.store.dto.response.UserResponse;
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
    ApiResponse<ProductResponse> createProduct(@RequestBody ProductCreationRequest request) {
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(productService.createProduct(request));
        return apiResponse;
    }

    @PutMapping("/{productId}")
    ApiResponse<ProductResponse> updateProduct(@PathVariable String productId, @RequestBody ProductUpdateRequest request) {
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(productService.updateProduct(productId, request));
        return apiResponse;
    }

    @DeleteMapping("/{productId}")
    ApiResponse<ProductResponse> deleteProduct(@PathVariable String productId) {
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(productService.deleteProduct(productId));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<ProductResponse>> getAllProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAllProducts())
                .build();
    }


    @GetMapping("/{productId}")
    ApiResponse<ProductResponse> getProduct(@PathVariable String productId) {
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(productService.getProduct(productId));
        return apiResponse;
    }
}
