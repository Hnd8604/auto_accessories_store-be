package app.store.controller;

import app.store.constant.ResponseMessage;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.yourproject.utils.SortUtils.buildSort;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductServiceImpl productServiceImpl;

    @PostMapping
    ApiResponse<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productServiceImpl.createProduct(request))
                .message(ResponseMessage.CREATE_PRODUCT_SUCCESS)
                .build();
    }

    @PutMapping("/{productId}")
    ApiResponse<ProductResponse> updateProduct(@PathVariable Long productId, @RequestBody ProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productServiceImpl.updateProduct(productId, request))
                .message(ResponseMessage.UPDATE_PRODUCT_SUCCESS)
                .build();
    }

    @DeleteMapping("/{productId}")
    ApiResponse<Void> deleteProduct(@PathVariable Long productId) {
        productServiceImpl.deleteProduct(productId);
        return ApiResponse.<Void>builder().message(ResponseMessage.DELETE_PRODUCT_SUCCESS).build();
    }

    @GetMapping
    ApiResponse<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "name,asc") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, buildSort(sort));
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productServiceImpl.getAllProducts(pageable))
                .message(ResponseMessage.GET_ALL_PRODUCTS_SUCCESS)
                .build();
    }

    @GetMapping("/categories/{categoryId}")
    ApiResponse<Page<ProductResponse>> getAllProductsByCategoryId(@PathVariable Long categoryId,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "2") int size,
                                                                  @RequestParam(defaultValue = "name,asc") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, buildSort(sort));
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productServiceImpl.getProductsByCategoryId(categoryId, pageable))
                .message(ResponseMessage.GET_ALL_PRODUCTS_BY_CATEGORY_SUCCESS)
                .build();
    }

    @GetMapping("/brands/{brandId}")
    ApiResponse<Page<ProductResponse>> getAllProductsByBrandId(@PathVariable Long brandId,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "2") int size,
                                                               @RequestParam(defaultValue = "name,asc") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, buildSort(sort));

        return ApiResponse.<Page<ProductResponse>>builder()
                .result(productServiceImpl.getProductsByBrandId(brandId, pageable))
                .message(ResponseMessage.GET_ALL_PRODUCTS_BY_BRAND_SUCCESS)
                .build();
    }

    @GetMapping("/{productId}")
    ApiResponse<ProductResponse> getProductById(@PathVariable Long productId) {
        return ApiResponse.<ProductResponse>builder()
                .result(productServiceImpl.getProductById(productId))
                .message(ResponseMessage.GET_PRODUCT_SUCCESS)
                .build();
    }
}

