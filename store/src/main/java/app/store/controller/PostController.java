package app.store.controller;

import app.store.constant.ResponseMessage;
import app.store.dto.request.PostRequest;
import app.store.dto.response.auth.ApiResponse;
import app.store.dto.response.PostResponse;
import app.store.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Post Management", description = "APIs for managing blog posts including CRUD operations, publishing, and public access")
public class PostController {
    
    private final PostService postService;
    
    // ==================== ADMIN APIs ====================
    
    @PostMapping("/api/admin/posts")
    @Operation(
        summary = "Create a new post",
        description = "Creates a new blog post. Only accessible by admin users. Automatically generates SEO-friendly slug from title and associates with authenticated user as author."
    )
    public ApiResponse<PostResponse> createPost(
            @Valid @RequestBody PostRequest request,
            Authentication authentication) {
        String authorId = authentication.getName(); // Lấy ID từ JWT token
        PostResponse response = postService.createPost(request, authorId);
        return ApiResponse.<PostResponse>builder()
                .message(ResponseMessage.CREATE_POST_SUCCESS)
                .result(response)
                .build();
    }
    
    @PutMapping("/api/admin/posts/{id}")
    @Operation(
        summary = "Update post",
        description = "Updates an existing blog post by ID. Only accessible by admin users. Slug will be regenerated if title is changed."
    )
    public ApiResponse<PostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest request) {
        PostResponse response = postService.updatePost(id, request);
        return ApiResponse.<PostResponse>builder()
                .message(ResponseMessage.UPDATE_POST_SUCCESS)
                .result(response)
                .build();
    }
    
    @DeleteMapping("/api/admin/posts/{id}")
    @Operation(
        summary = "Delete post",
        description = "Permanently deletes a blog post by ID. Only accessible by admin users."
    )
    public ApiResponse<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ApiResponse.<Void>builder()
                .message(ResponseMessage.DELETE_POST_SUCCESS)
                .build();
    }
    
    @GetMapping("/api/admin/posts/{id}")
    @Operation(
        summary = "Get post by ID",
        description = "Retrieves detailed information of a post by ID including draft posts. Only accessible by admin users."
    )
    public ApiResponse<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse response = postService.getPostById(id);
        return ApiResponse.<PostResponse>builder()
                .message(ResponseMessage.GET_POST_SUCCESS)
                .result(response)
                .build();
    }
    
    @GetMapping("/api/admin/posts")
    @Operation(
        summary = "Get all posts",
        description = "Retrieves all posts with pagination including drafts. Only accessible by admin users. Sorted by creation date descending."
    )
    public ApiResponse<Page<PostResponse>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostResponse> response = postService.getAllPosts(page, size);
        return ApiResponse.<Page<PostResponse>>builder()
                .message(ResponseMessage.GET_ALL_POSTS_SUCCESS)
                .result(response)
                .build();
    }
    
    @PatchMapping("/api/admin/posts/{id}/toggle-publish")
    @Operation(
        summary = "Toggle publish status",
        description = "Toggles the publish status of a post (publish/unpublish). Only accessible by admin users."
    )
    public ApiResponse<Void> togglePublishStatus(@PathVariable Long id) {
        postService.togglePublishStatus(id);
        return ApiResponse.<Void>builder()
                .message(ResponseMessage.TOGGLE_POST_PUBLISH_SUCCESS)
                .build();
    }
    
    // ==================== PUBLIC APIs ====================
    
    @GetMapping("/api/posts")
    @Operation(
        summary = "Get published posts",
        description = "Retrieves all published posts with pagination. Accessible by authenticated users. Only returns posts with published status."
    )
    public ApiResponse<Page<PostResponse>> getPublishedPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostResponse> response = postService.getPublishedPosts(page, size);
        return ApiResponse.<Page<PostResponse>>builder()
                .message(ResponseMessage.GET_ALL_POSTS_SUCCESS)
                .result(response)
                .build();
    }
    
    @GetMapping("/api/posts/search")
    @Operation(
        summary = "Search published posts",
        description = "Searches published posts by keyword with pagination. Accessible by authenticated users. Searches in title and short description fields only."
    )
    public ApiResponse<Page<PostResponse>> searchPosts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostResponse> response = postService.searchPublishedPosts(keyword, page, size);
        return ApiResponse.<Page<PostResponse>>builder()
                .message(ResponseMessage.SEARCH_POSTS_SUCCESS)
                .result(response)
                .build();
    }
    
    @GetMapping("/api/posts/{slug}")
    @Operation(
        summary = "Get post by slug",
        description = "Retrieves post details by slug and automatically increments view count. Accessible by authenticated users. Slug is SEO-friendly URL identifier."
    )
    public ApiResponse<PostResponse> getPostBySlug(@PathVariable String slug) {
        PostResponse response = postService.getPostBySlugAndIncrementView(slug);
        return ApiResponse.<PostResponse>builder()
                .message(ResponseMessage.GET_POST_SUCCESS)
                .result(response)
                .build();
    }
    
    @GetMapping("/api/posts/category/{categoryId}")
    @Operation(
        summary = "Get posts by category",
        description = "Retrieves published posts filtered by category with pagination. Accessible by authenticated users."
    )
    public ApiResponse<Page<PostResponse>> getPostsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PostResponse> response = postService.getPostsByCategory(categoryId, page, size);
        return ApiResponse.<Page<PostResponse>>builder()
                .message(ResponseMessage.GET_POSTS_BY_CATEGORY_SUCCESS)
                .result(response)
                .build();
    }
    
    @GetMapping("/api/posts/{id}/related")
    @Operation(
        summary = "Get related posts",
        description = "Retrieves related posts from the same category. Accessible by authenticated users. Returns posts sorted by creation date."
    )
    public ApiResponse<List<PostResponse>> getRelatedPosts(
            @PathVariable Long id,
            @RequestParam(defaultValue = "5") int limit) {
        List<PostResponse> response = postService.getRelatedPosts(id, limit);
        return ApiResponse.<List<PostResponse>>builder()
                .message(ResponseMessage.GET_RELATED_POSTS_SUCCESS)
                .result(response)
                .build();
    }
    
    @GetMapping("/api/posts/most-viewed")
    @Operation(
        summary = "Get most viewed posts",
        description = "Retrieves the most viewed published posts. Accessible by authenticated users. Sorted by view count descending."
    )
    public ApiResponse<List<PostResponse>> getMostViewedPosts(
            @RequestParam(defaultValue = "5") int limit) {
        List<PostResponse> response = postService.getMostViewedPosts(limit);
        return ApiResponse.<List<PostResponse>>builder()
                .message(ResponseMessage.GET_MOST_VIEWED_POSTS_SUCCESS)
                .result(response)
                .build();
    }
}
