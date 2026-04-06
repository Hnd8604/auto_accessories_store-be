package app.store.controller;

import app.store.constant.ResponseMessage;
import app.store.dto.request.GalleryCategoryRequest;
import app.store.dto.response.GalleryCategoryResponse;
import app.store.dto.response.auth.ApiResponse;
import app.store.service.GalleryCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/gallery-categories")
@Tag(name = "Gallery Category Management", description = "APIs for managing gallery categories")
public class GalleryCategoryController {

    GalleryCategoryService galleryCategoryService;

    @GetMapping
    @Operation(summary = "Get all gallery categories", description = "Retrieves all gallery categories sorted by sortOrder. Public access.")
    public ApiResponse<List<GalleryCategoryResponse>> getAll() {
        List<GalleryCategoryResponse> response = galleryCategoryService.getAll();
        return ApiResponse.<List<GalleryCategoryResponse>>builder()
                .message(ResponseMessage.GET_ALL_GALLERY_CATEGORIES_SUCCESS)
                .result(response)
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get gallery category by ID", description = "Retrieves a gallery category by its ID.")
    public ApiResponse<GalleryCategoryResponse> getById(@PathVariable Long id) {
        GalleryCategoryResponse response = galleryCategoryService.getById(id);
        return ApiResponse.<GalleryCategoryResponse>builder()
                .message(ResponseMessage.GET_GALLERY_CATEGORY_SUCCESS)
                .result(response)
                .build();
    }

    @PostMapping
    @Operation(summary = "Create gallery category", description = "Creates a new gallery category. Admin only.")
    public ApiResponse<GalleryCategoryResponse> create(@Valid @RequestBody GalleryCategoryRequest request) {
        GalleryCategoryResponse response = galleryCategoryService.create(request);
        return ApiResponse.<GalleryCategoryResponse>builder()
                .message(ResponseMessage.CREATE_GALLERY_CATEGORY_SUCCESS)
                .result(response)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update gallery category", description = "Updates an existing gallery category. Admin only.")
    public ApiResponse<GalleryCategoryResponse> update(@PathVariable Long id, @Valid @RequestBody GalleryCategoryRequest request) {
        GalleryCategoryResponse response = galleryCategoryService.update(id, request);
        return ApiResponse.<GalleryCategoryResponse>builder()
                .message(ResponseMessage.UPDATE_GALLERY_CATEGORY_SUCCESS)
                .result(response)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete gallery category", description = "Deletes a gallery category. Admin only.")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        galleryCategoryService.delete(id);
        return ApiResponse.<Void>builder()
                .message(ResponseMessage.DELETE_GALLERY_CATEGORY_SUCCESS)
                .build();
    }
}
