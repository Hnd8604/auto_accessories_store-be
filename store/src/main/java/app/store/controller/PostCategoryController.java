package app.store.controller;

import app.store.constant.ResponseMessage;
import app.store.dto.request.PostCategoryRequest;
import app.store.dto.response.auth.ApiResponse;
import app.store.dto.response.PostCategoryResponse;
import app.store.service.interfaces.PostCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Post Category Management", description = "APIs for managing post categories (blog categories)")
public class PostCategoryController {
    
    private final PostCategoryService postCategoryService;
    
    // ==================== ADMIN APIs ====================
    
    @PostMapping("/api/admin/post-categories")
    @Operation(
        summary = "Create a new post category",
        description = "Creates a new post category. Only accessible by admin users. Automatically generates SEO-friendly slug from the category name."
    )
    public ApiResponse<PostCategoryResponse> createCategory(@Valid @RequestBody PostCategoryRequest request) {
        PostCategoryResponse response = postCategoryService.createCategory(request);
        return ApiResponse.<PostCategoryResponse>builder()
                .message(ResponseMessage.CREATE_POST_CATEGORY_SUCCESS)
                .result(response)
                .build();
    }
    
    @PutMapping("/api/admin/post-categories/{id}")
    @Operation(
        summary = "Update post category",
        description = "Updates an existing post category by ID. Only accessible by admin users. Slug will be regenerated if name is changed."
    )
    public ApiResponse<PostCategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody PostCategoryRequest request) {
        PostCategoryResponse response = postCategoryService.updateCategory(id, request);
        return ApiResponse.<PostCategoryResponse>builder()
                .message(ResponseMessage.UPDATE_POST_CATEGORY_SUCCESS)
                .result(response)
                .build();
    }
    
    @DeleteMapping("/api/admin/post-categories/{id}")
    @Operation(
        summary = "Delete post category",
        description = "Deletes a post category by ID. Only accessible by admin users. Cannot delete categories that contain posts."
    )
    public ApiResponse<Void> deleteCategory(@PathVariable Long id) {
        postCategoryService.deleteCategory(id);
        return ApiResponse.<Void>builder()
                .message(ResponseMessage.DELETE_POST_CATEGORY_SUCCESS)
                .build();
    }
    
    @GetMapping("/api/admin/post-categories/{id}")
    @Operation(
        summary = "Get post category by ID",
        description = "Retrieves detailed information of a post category by ID. Only accessible by admin users."
    )
    public ApiResponse<PostCategoryResponse> getCategoryById(@PathVariable Long id) {
        PostCategoryResponse response = postCategoryService.getCategoryById(id);
        return ApiResponse.<PostCategoryResponse>builder()
                .message(ResponseMessage.GET_POST_CATEGORY_SUCCESS)
                .result(response)
                .build();
    }
    
    @GetMapping("/api/admin/post-categories")
    @Operation(
        summary = "Search post categories",
        description = "Searches post categories with pagination. Only accessible by admin users. Supports keyword search in name and description."
    )
    public ApiResponse<Page<PostCategoryResponse>> searchCategories(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostCategoryResponse> response = postCategoryService.searchCategories(keyword, page, size);
        return ApiResponse.<Page<PostCategoryResponse>>builder()
                .message(ResponseMessage.SEARCH_POST_CATEGORIES_SUCCESS)
                .result(response)
                .build();
    }
    
    // ==================== PUBLIC APIs ====================
    
    @GetMapping("/api/post-categories")
    @Operation(
        summary = "Get all post categories",
        description = "Retrieves all post categories sorted by name. Accessible by authenticated users."
    )
    public ApiResponse<List<PostCategoryResponse>> getAllCategories() {
        List<PostCategoryResponse> response = postCategoryService.getAllCategories();
        return ApiResponse.<List<PostCategoryResponse>>builder()
                .message(ResponseMessage.GET_ALL_POST_CATEGORIES_SUCCESS)
                .result(response)
                .build();
    }
    
    @GetMapping("/api/post-categories/{slug}")
    @Operation(
        summary = "Get post category by slug",
        description = "Retrieves post category information by slug. Accessible by authenticated users. Slug is SEO-friendly URL identifier."
    )
    public ApiResponse<PostCategoryResponse> getCategoryBySlug(@PathVariable String slug) {
        PostCategoryResponse response = postCategoryService.getCategoryBySlug(slug);
        return ApiResponse.<PostCategoryResponse>builder()
                .message(ResponseMessage.GET_POST_CATEGORY_SUCCESS)
                .result(response)
                .build();
    }
}
