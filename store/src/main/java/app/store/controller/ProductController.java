package app.store.controller;

import app.store.dto.request.ProductRequest;
import app.store.dto.response.auth.ApiResponse;
import app.store.dto.response.ProductResponse;
import app.store.service.implementation.ProductServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductServiceImpl productServiceImpl;

    @PostMapping
    ApiResponse<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(productServiceImpl.createProduct(request));
        return apiResponse;
    }

    @PutMapping("/{productId}")
    ApiResponse<ProductResponse> updateProduct(@PathVariable Long productId, @RequestBody ProductRequest request) {
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(productServiceImpl.updateProduct(productId, request));
        return apiResponse;
    }

    @DeleteMapping("/{productId}")
    ApiResponse<ProductResponse> deleteProduct(@PathVariable Long productId) {
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(productServiceImpl.deleteProduct(productId));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<Page<ProductResponse>> getAllProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "2") int size,
            @RequestParam(value = "sort", defaultValue = "productName,asc") String sort
    ) {
        Sort pageSort;
        String[] sortPart = sort.split(",");
        String direction = sortPart.length > 1 ? sortPart[1] : "asc";
        String sortField = sortPart[0].trim();
        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        String actualSortField = "productName".equalsIgnoreCase(sortField) ? "productName" : sortField;

        pageSort = Sort.by(sortDirection, actualSortField);

        Pageable pageable = PageRequest.of(page, size, pageSort);
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productServiceImpl.getAllProducts(pageable))
                .build();
    }

    @GetMapping("/category/{categoryId}")
    ApiResponse<List<ProductResponse>> getAllProductsByCategory(@PathVariable Long categoryId) {
        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>();

        apiResponse.setResult(productServiceImpl.getProductsByCategoryId(categoryId));
        return apiResponse;
    }
    @GetMapping("/{productId}")
    ApiResponse<ProductResponse> getProduct(@PathVariable Long productId) {
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(productServiceImpl.getProduct(productId));
        return apiResponse;
    }
}
