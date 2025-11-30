package app.store.controller;

import app.store.dto.request.PostRequest;
import app.store.dto.response.PostResponse;
import app.store.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Post", description = "API quản lý bài viết")
public class PostController {
    
    private final PostService postService;
    
    // ==================== ADMIN APIs ====================
    
    @PostMapping("/api/admin/posts")
    @Operation(summary = "Tạo bài viết mới", description = "Admin tạo bài viết mới")
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody PostRequest request,
            Authentication authentication) {
        String authorId = authentication.getName(); // Lấy ID từ JWT token
        PostResponse response = postService.createPost(request, authorId);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/api/admin/posts/{id}")
    @Operation(summary = "Cập nhật bài viết", description = "Admin cập nhật thông tin bài viết")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest request) {
        PostResponse response = postService.updatePost(id, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/api/admin/posts/{id}")
    @Operation(summary = "Xóa bài viết", description = "Admin xóa bài viết")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/api/admin/posts/{id}")
    @Operation(summary = "Lấy bài viết theo ID", description = "Admin lấy chi tiết bài viết theo ID")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/api/admin/posts")
    @Operation(summary = "Lấy tất cả bài viết", description = "Admin lấy danh sách tất cả bài viết (bao gồm draft)")
    public ResponseEntity<Page<PostResponse>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostResponse> response = postService.getAllPosts(page, size);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/api/admin/posts/{id}/toggle-publish")
    @Operation(summary = "Bật/tắt trạng thái xuất bản", description = "Admin bật/tắt trạng thái xuất bản bài viết")
    public ResponseEntity<Void> togglePublishStatus(@PathVariable Long id) {
        postService.togglePublishStatus(id);
        return ResponseEntity.ok().build();
    }
    
    // ==================== PUBLIC APIs ====================
    
    @GetMapping("/api/posts")
    @Operation(summary = "Lấy danh sách bài viết đã xuất bản", description = "Lấy danh sách bài viết đã xuất bản với phân trang")
    public ResponseEntity<Page<PostResponse>> getPublishedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostResponse> response = postService.getPublishedPosts(page, size);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/api/posts/search")
    @Operation(summary = "Tìm kiếm bài viết", description = "Tìm kiếm bài viết đã xuất bản theo từ khóa")
    public ResponseEntity<Page<PostResponse>> searchPosts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostResponse> response = postService.searchPublishedPosts(keyword, page, size);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/api/posts/{slug}")
    @Operation(summary = "Xem chi tiết bài viết", description = "Xem chi tiết bài viết theo slug (tăng view count)")
    public ResponseEntity<PostResponse> getPostBySlug(@PathVariable String slug) {
        PostResponse response = postService.getPostBySlugAndIncrementView(slug);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/api/posts/category/{categoryId}")
    @Operation(summary = "Lấy bài viết theo danh mục", description = "Lấy danh sách bài viết đã xuất bản theo danh mục")
    public ResponseEntity<Page<PostResponse>> getPostsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostResponse> response = postService.getPostsByCategory(categoryId, page, size);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/api/posts/{id}/related")
    @Operation(summary = "Lấy bài viết liên quan", description = "Lấy danh sách bài viết liên quan cùng danh mục")
    public ResponseEntity<List<PostResponse>> getRelatedPosts(
            @PathVariable Long id,
            @RequestParam(defaultValue = "5") int limit) {
        List<PostResponse> response = postService.getRelatedPosts(id, limit);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/api/posts/most-viewed")
    @Operation(summary = "Bài viết được xem nhiều nhất", description = "Lấy danh sách bài viết được xem nhiều nhất")
    public ResponseEntity<List<PostResponse>> getMostViewedPosts(
            @RequestParam(defaultValue = "5") int limit) {
        List<PostResponse> response = postService.getMostViewedPosts(limit);
        return ResponseEntity.ok(response);
    }
}
