package app.store.controller;

import app.store.constant.ResponseMessage;
import app.store.dto.request.CategoryRequest;
import app.store.dto.response.CategoryResponse;
import app.store.dto.response.auth.ApiResponse;
import app.store.service.implementation.CategoryServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

import static com.yourproject.utils.SortUtils.buildSort;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryServiceImpl categoryServiceImpl;

    @PostMapping
    ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {

        return ApiResponse.<CategoryResponse>builder()
                .result(categoryServiceImpl.createCategory(request))
                .message(ResponseMessage.CREATE_CATEGORY_SUCCESS)
                .build();
    }

    @GetMapping
    ApiResponse<Page<CategoryResponse>> getAllCategories(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "2") int size,
                                                         @RequestParam(defaultValue = "name,asc") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, buildSort(sort));return ApiResponse.<Page<CategoryResponse>>builder()
                .result(categoryServiceImpl.getAllCategories(pageable))
                .message(ResponseMessage.GET_ALL_CATEGORIES_SUCCESS)
                .build();
    }

    @GetMapping("/{categoryId}")
    ApiResponse<CategoryResponse> getCategory(@PathVariable Long categoryId) {
       return ApiResponse.<CategoryResponse>builder()
                .result(categoryServiceImpl.getCategoryById(categoryId))
               .message(ResponseMessage.GET_CATEGORY_SUCCESS)
                .build();
    }
    @PutMapping("/{categoryId}")
    ApiResponse<CategoryResponse> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryServiceImpl.updateCategory(categoryId, request))
                .message(ResponseMessage.UPDATE_CATEGORY_SUCCESS)
                .build();
    }

    @DeleteMapping("/{categoryId}")
    ApiResponse<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryServiceImpl.deleteCategory(categoryId);
        return ApiResponse.<Void>builder()
                .message(ResponseMessage.DELETE_CATEGORY_SUCCESS)
                .build();
    }

}
