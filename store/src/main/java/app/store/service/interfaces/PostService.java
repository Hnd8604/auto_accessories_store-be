package app.store.service.interfaces;

import app.store.dto.request.PostRequest;
import app.store.dto.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    
    PostResponse createPost(PostRequest request, String authorId);
    
    PostResponse updatePost(Long id, PostRequest request);
    
    void deletePost(Long id);
    
    PostResponse getPostById(Long id);
    
    PostResponse getPostBySlug(String slug);
    
    PostResponse getPostBySlugAndIncrementView(String slug);
    
    Page<PostResponse> getAllPosts(Pageable pageable);
    
    Page<PostResponse> getPublishedPosts(Pageable pageable);
    
    Page<PostResponse> searchPublishedPosts(String keyword, Pageable pageable);
    
    Page<PostResponse> getPostsByCategory(Long categoryId, Pageable pageable);
    
    Page<PostResponse> getRelatedPosts(Long postId, Pageable pageable);
    
    Page<PostResponse> getMostViewedPosts(Pageable pageable);
    
    void togglePublishStatus(Long id);
}
