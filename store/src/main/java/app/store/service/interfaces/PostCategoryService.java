package app.store.service.interfaces;

import app.store.dto.request.PostCategoryRequest;
import app.store.dto.response.PostCategoryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostCategoryService {
    
    PostCategoryResponse createCategory(PostCategoryRequest request);
    
    PostCategoryResponse updateCategory(Long id, PostCategoryRequest request);
    
    void deleteCategory(Long id);
    
    PostCategoryResponse getCategoryById(Long id);
    
    PostCategoryResponse getCategoryBySlug(String slug);
    
    List<PostCategoryResponse> getAllCategories();
    
    List<PostCategoryResponse> searchCategories(String keyword);
}
