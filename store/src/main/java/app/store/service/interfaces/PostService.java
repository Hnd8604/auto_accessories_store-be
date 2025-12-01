package app.store.service.interfaces;

import app.store.dto.request.PostRequest;
import app.store.dto.response.PostResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    
    PostResponse createPost(PostRequest request, String authorId);
    
    PostResponse updatePost(Long id, PostRequest request);
    
    void deletePost(Long id);
    
    PostResponse getPostById(Long id);
    
    PostResponse getPostBySlug(String slug);
    
    PostResponse getPostBySlugAndIncrementView(String slug);
    
    Page<PostResponse> getAllPosts(int page, int size);
    
    Page<PostResponse> getPublishedPosts(int page, int size);
    
    Page<PostResponse> searchPublishedPosts(String keyword, int page, int size);
    
    Page<PostResponse> getPostsByCategory(Long categoryId, int page, int size);
    
    List<PostResponse> getRelatedPosts(Long postId, int limit);
    
    List<PostResponse> getMostViewedPosts(int limit);
    
    void togglePublishStatus(Long id);
}
