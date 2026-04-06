package app.store.controller;

import app.store.constant.ResponseMessage;
import app.store.dto.request.GalleryItemRequest;
import app.store.dto.response.GalleryItemResponse;
import app.store.dto.response.auth.ApiResponse;
import app.store.service.GalleryItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static app.store.utils.SortUtils.buildSort;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/gallery-items")
@Tag(name = "Gallery Item Management", description = "APIs for managing gallery items (car modification showcases)")
public class GalleryItemController {

    GalleryItemService galleryItemService;

    // === PUBLIC ENDPOINTS ===

    @GetMapping("/published")
    @Operation(summary = "Get published gallery items", description = "Retrieves all published gallery items with pagination. Public access.")
    public ApiResponse<Page<GalleryItemResponse>> getPublished(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "createdAt,DESC") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, buildSort(sort));
        Page<GalleryItemResponse> response = galleryItemService.getPublishedItems(pageable);
        return ApiResponse.<Page<GalleryItemResponse>>builder()
                .message(ResponseMessage.GET_ALL_GALLERY_ITEMS_SUCCESS)
                .result(response)
                .build();
    }

    @GetMapping("/slug/{slug}")
    @Operation(summary = "Get gallery item by slug", description = "Retrieves a gallery item by slug and increments view count. Public access.")
    public ApiResponse<GalleryItemResponse> getBySlug(@PathVariable String slug) {
        GalleryItemResponse response = galleryItemService.getBySlugAndIncrementView(slug);
        return ApiResponse.<GalleryItemResponse>builder()
                .message(ResponseMessage.GET_GALLERY_ITEM_SUCCESS)
                .result(response)
                .build();
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get gallery items by category", description = "Retrieves published gallery items filtered by category. Public access.")
    public ApiResponse<Page<GalleryItemResponse>> getByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "createdAt,DESC") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, buildSort(sort));
        Page<GalleryItemResponse> response = galleryItemService.getByCategory(categoryId, pageable);
        return ApiResponse.<Page<GalleryItemResponse>>builder()
                .message(ResponseMessage.GET_GALLERY_ITEMS_BY_CATEGORY_SUCCESS)
                .result(response)
                .build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search published gallery items", description = "Searches published gallery items by keyword. Public access.")
    public ApiResponse<Page<GalleryItemResponse>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "createdAt,DESC") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, buildSort(sort));
        Page<GalleryItemResponse> response = galleryItemService.searchPublished(keyword, pageable);
        return ApiResponse.<Page<GalleryItemResponse>>builder()
                .message(ResponseMessage.SEARCH_GALLERY_ITEMS_SUCCESS)
                .result(response)
                .build();
    }

    // === ADMIN ENDPOINTS ===

    @GetMapping
    @Operation(summary = "Get all gallery items", description = "Retrieves all gallery items including drafts. Admin only.")
    public ApiResponse<Page<GalleryItemResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,DESC") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, buildSort(sort));
        Page<GalleryItemResponse> response = galleryItemService.getAllItems(pageable);
        return ApiResponse.<Page<GalleryItemResponse>>builder()
                .message(ResponseMessage.GET_ALL_GALLERY_ITEMS_SUCCESS)
                .result(response)
                .build();
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Get gallery item by ID", description = "Retrieves gallery item details by ID. Admin only.")
    public ApiResponse<GalleryItemResponse> getById(@PathVariable Long id) {
        GalleryItemResponse response = galleryItemService.getById(id);
        return ApiResponse.<GalleryItemResponse>builder()
                .message(ResponseMessage.GET_GALLERY_ITEM_SUCCESS)
                .result(response)
                .build();
    }

    @PostMapping
    @Operation(summary = "Create gallery item", description = "Creates a new gallery item with thumbnail and optional detail images. Admin only.")
    public ApiResponse<GalleryItemResponse> create(
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestPart(value = "images", required = false) List<MultipartFile> images,
            @Valid @RequestPart("item") GalleryItemRequest request
    ) {
        GalleryItemResponse response = galleryItemService.createItem(thumbnail, images, request);
        return ApiResponse.<GalleryItemResponse>builder()
                .message(ResponseMessage.CREATE_GALLERY_ITEM_SUCCESS)
                .result(response)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update gallery item", description = "Updates an existing gallery item. Admin only.")
    public ApiResponse<GalleryItemResponse> update(
            @PathVariable Long id,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
            @RequestPart(value = "images", required = false) List<MultipartFile> newImages,
            @Valid @RequestPart("item") GalleryItemRequest request
    ) {
        GalleryItemResponse response = galleryItemService.updateItem(id, thumbnail, newImages, request);
        return ApiResponse.<GalleryItemResponse>builder()
                .message(ResponseMessage.UPDATE_GALLERY_ITEM_SUCCESS)
                .result(response)
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete gallery item", description = "Deletes a gallery item and all its images. Admin only.")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        galleryItemService.deleteItem(id);
        return ApiResponse.<Void>builder()
                .message(ResponseMessage.DELETE_GALLERY_ITEM_SUCCESS)
                .build();
    }

    @DeleteMapping("/{itemId}/images/{imageId}")
    @Operation(summary = "Delete a gallery image", description = "Deletes a specific image from a gallery item. Admin only.")
    public ApiResponse<Void> deleteImage(@PathVariable Long itemId, @PathVariable Long imageId) {
        galleryItemService.deleteImage(itemId, imageId);
        return ApiResponse.<Void>builder()
                .message(ResponseMessage.DELETE_GALLERY_IMAGE_SUCCESS)
                .build();
    }

    @PatchMapping("/{id}/toggle-publish")
    @Operation(summary = "Toggle publish status", description = "Toggles the publish status of a gallery item. Admin only.")
    public ApiResponse<Void> togglePublish(@PathVariable Long id) {
        galleryItemService.togglePublish(id);
        return ApiResponse.<Void>builder()
                .message(ResponseMessage.TOGGLE_GALLERY_ITEM_PUBLISH_SUCCESS)
                .build();
    }
}
