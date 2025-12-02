package app.store.controller;

import app.store.constant.ResponseMessage;
import app.store.dto.request.CategoryRequest;
import app.store.dto.response.CategoryResponse;
import app.store.dto.response.auth.ApiResponse;
import app.store.service.impl.CategoryServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.experimental.FieldDefaults;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Category Management", description = "APIs for managing product categories including CRUD operations")
public class CategoryController {
    CategoryServiceImpl categoryServiceImpl;

    @PostMapping
    @Operation(
        summary = "Create a new category",
        description = "Creates a new product category. Only accessible by admin users."
    )
    ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {

        return ApiResponse.<CategoryResponse>builder()
                .result(categoryServiceImpl.createCategory(request))
                .message(ResponseMessage.CREATE_CATEGORY_SUCCESS)
                .build();
    }

    @GetMapping
    @Operation(
        summary = "Get all categories",
        description = "Retrieves all product categories with pagination and sorting. Accessible by authenticated users."
    )
    ApiResponse<List<CategoryResponse>> getAllCategories() {

        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryServiceImpl.getAllCategories())
                .message(ResponseMessage.GET_ALL_CATEGORIES_SUCCESS)
                .build();
    }

    @GetMapping("/{categoryId}")
    @Operation(
        summary = "Get category by ID",
        description = "Retrieves detailed information of a category by ID. Accessible by authenticated users."
    )
    ApiResponse<CategoryResponse> getCategory(@PathVariable Long categoryId) {
       return ApiResponse.<CategoryResponse>builder()
                .result(categoryServiceImpl.getCategoryById(categoryId))
               .message(ResponseMessage.GET_CATEGORY_SUCCESS)
                .build();
    }
    @PutMapping("/{categoryId}")
    @Operation(
        summary = "Update category",
        description = "Updates an existing product category by ID. Only accessible by admin users."
    )
    ApiResponse<CategoryResponse> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryServiceImpl.updateCategory(categoryId, request))
                .message(ResponseMessage.UPDATE_CATEGORY_SUCCESS)
                .build();
    }

    @DeleteMapping("/{categoryId}")
    @Operation(
        summary = "Delete category",
        description = "Permanently deletes a product category by ID. Only accessible by admin users. Cannot delete categories with products."
    )
    ApiResponse<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryServiceImpl.deleteCategory(categoryId);
        return ApiResponse.<Void>builder()
                .message(ResponseMessage.DELETE_CATEGORY_SUCCESS)
                .build();
    }

}
