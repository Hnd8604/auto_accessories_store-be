package app.store.service;

import app.store.dto.response.CategoryResponse;
import app.store.dto.request.CategoryRequest;
import app.store.entity.Category;
import app.store.mapper.CategoryMapper;
import app.store.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService {
    CategoryMapper categoryMapper;
    CategoryRepository categoryRepository;

    public CategoryResponse createCategory(CategoryRequest request) {


        Category category = categoryMapper.toCategory(request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public CategoryResponse getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(categoryMapper::toCategoryResponse)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryMapper.updateCategory(category, request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(categoryId);
    }
}
