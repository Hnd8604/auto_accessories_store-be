package app.store.controller;

import app.store.dto.request.PostCategoryRequest;
import app.store.dto.response.PostCategoryResponse;
import app.store.service.interfaces.PostCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Post Category", description = "API quản lý danh mục bài viết")
public class PostCategoryController {
    
    private final PostCategoryService postCategoryService;
    
    // ==================== ADMIN APIs ====================
    
    @PostMapping("/api/admin/post-categories")
    @Operation(summary = "Tạo danh mục bài viết mới", description = "Admin tạo danh mục bài viết mới")
    public ResponseEntity<PostCategoryResponse> createCategory(@Valid @RequestBody PostCategoryRequest request) {
        PostCategoryResponse response = postCategoryService.createCategory(request);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/api/admin/post-categories/{id}")
    @Operation(summary = "Cập nhật danh mục bài viết", description = "Admin cập nhật thông tin danh mục bài viết")
    public ResponseEntity<PostCategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody PostCategoryRequest request) {
        PostCategoryResponse response = postCategoryService.updateCategory(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/api/admin/post-categories/{id}")
    @Operation(summary = "Xóa danh mục bài viết", description = "Admin xóa danh mục bài viết")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        postCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/api/admin/post-categories/{id}")
    @Operation(summary = "Lấy thông tin danh mục theo ID", description = "Admin lấy chi tiết danh mục bài viết")
    public ResponseEntity<PostCategoryResponse> getCategoryById(@PathVariable Long id) {
        PostCategoryResponse response = postCategoryService.getCategoryById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/api/admin/post-categories")
    @Operation(summary = "Tìm kiếm danh mục bài viết", description = "Admin tìm kiếm và phân trang danh mục bài viết")
    public ResponseEntity<Page<PostCategoryResponse>> searchCategories(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostCategoryResponse> response = postCategoryService.searchCategories(keyword, page, size);
        return ResponseEntity.ok(response);
    }
    
    // ==================== PUBLIC APIs ====================
    
    @GetMapping("/api/post-categories")
    @Operation(summary = "Lấy tất cả danh mục bài viết", description = "Lấy danh sách tất cả danh mục bài viết cho public")
    public ResponseEntity<List<PostCategoryResponse>> getAllCategories() {
        List<PostCategoryResponse> response = postCategoryService.getAllCategories();
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/api/post-categories/{slug}")
    @Operation(summary = "Lấy danh mục theo slug", description = "Lấy thông tin danh mục bài viết theo slug")
    public ResponseEntity<PostCategoryResponse> getCategoryBySlug(@PathVariable String slug) {
        PostCategoryResponse response = postCategoryService.getCategoryBySlug(slug);
        return ResponseEntity.ok(response);
    }
}
