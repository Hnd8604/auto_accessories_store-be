package app.store.service.impl;

import app.store.dto.response.CategoryResponse;
import app.store.dto.request.CategoryRequest;
import app.store.entity.Category;
import app.store.exception.AppException;
import app.store.exception.ErrorCode;
import app.store.mapper.CategoryMapper;
import app.store.repository.CategoryRepository;
import app.store.service.interfaces.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    CategoryMapper categoryMapper;
    CategoryRepository categoryRepository;

    @Override
    @PreAuthorize("hasAuthority('CATEGORY_CREATE')")
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = categoryMapper.toCategory(request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    @PreAuthorize("hasAuthority('CATEGORY_GET_BY_ID')")
    public CategoryResponse getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(categoryMapper::toCategoryResponse)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
    }
    @Override
    @PreAuthorize("hasAuthority('CATEGORY_GET_ALL')")
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toCategoryResponse);
    }

    @Override
    @PreAuthorize("hasAuthority('CATEGORY_UPDATE')")
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        categoryMapper.updateCategory(category, request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    @PreAuthorize("hasAuthority('CATEGORY_DELETE')")
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        categoryRepository.deleteById(categoryId);

    }
}
