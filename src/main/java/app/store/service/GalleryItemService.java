package app.store.service;

import app.store.dto.request.GalleryItemRequest;
import app.store.dto.response.GalleryItemResponse;
import app.store.entity.GalleryCategory;
import app.store.entity.GalleryImage;
import app.store.entity.GalleryItem;
import app.store.mapper.GalleryItemMapper;
import app.store.repository.GalleryCategoryRepository;
import app.store.repository.GalleryImageRepository;
import app.store.repository.GalleryItemRepository;
import app.store.utils.SlugUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GalleryItemService {

    GalleryItemRepository galleryItemRepository;
    GalleryCategoryRepository galleryCategoryRepository;
    GalleryImageRepository galleryImageRepository;
    GalleryItemMapper galleryItemMapper;
    SlugUtil slugUtil;
    CloudinaryService cloudinaryService;

    @PreAuthorize("hasAuthority('GALLERY_CREATE')")
    public GalleryItemResponse createItem(MultipartFile thumbnail, List<MultipartFile> images, GalleryItemRequest request) {
        GalleryItem item = galleryItemMapper.toEntity(request);

        // Set category
        if (request.getCategoryId() != null) {
            GalleryCategory category = galleryCategoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục gallery với ID: " + request.getCategoryId()));
            item.setCategory(category);
        }

        // Generate slug
        String baseSlug = slugUtil.toSlug(request.getTitle());
        String uniqueSlug = slugUtil.createUniqueSlug(baseSlug, galleryItemRepository::existsBySlug);
        item.setSlug(uniqueSlug);
        item.setViewCount(0L);

        // Upload thumbnail
        if (thumbnail != null && !thumbnail.isEmpty()) {
            String thumbnailUrl = cloudinaryService.uploadImage(thumbnail, "store/gallery");
            item.setThumbnailUrl(thumbnailUrl);
        }

        // Save item first to get ID
        item = galleryItemRepository.save(item);

        // Upload and save detail images
        if (images != null && !images.isEmpty()) {
            for (int i = 0; i < images.size(); i++) {
                MultipartFile imageFile = images.get(i);
                if (imageFile != null && !imageFile.isEmpty()) {
                    String imageUrl = cloudinaryService.uploadImage(imageFile, "store/gallery/details");
                    GalleryImage galleryImage = GalleryImage.builder()
                            .galleryItem(item)
                            .imageUrl(imageUrl)
                            .sortOrder(i)
                            .build();
                    item.getImages().add(galleryImage);
                }
            }
            item = galleryItemRepository.save(item);
        }

        return galleryItemMapper.toResponse(item);
    }

    @PreAuthorize("hasAuthority('GALLERY_UPDATE')")
    public GalleryItemResponse updateItem(Long id, MultipartFile thumbnail, List<MultipartFile> newImages, GalleryItemRequest request) {
        GalleryItem item = galleryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác phẩm gallery với ID: " + id));

        // Update category
        if (request.getCategoryId() != null) {
            GalleryCategory category = galleryCategoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục gallery với ID: " + request.getCategoryId()));
            item.setCategory(category);
        } else {
            item.setCategory(null);
        }

        // Update slug if title changed
        if (!item.getTitle().equals(request.getTitle())) {
            String baseSlug = slugUtil.toSlug(request.getTitle());
            String uniqueSlug = slugUtil.createUniqueSlug(baseSlug, slug ->
                    !slug.equals(item.getSlug()) && galleryItemRepository.existsBySlug(slug));
            item.setSlug(uniqueSlug);
        }

        // Update fields
        item.setTitle(request.getTitle());
        item.setDescription(request.getDescription());
        item.setCarModel(request.getCarModel());
        if (request.getPublished() != null) {
            item.setPublished(request.getPublished());
        }

        // Update thumbnail if new file provided
        if (thumbnail != null && !thumbnail.isEmpty()) {
            String thumbnailUrl = cloudinaryService.uploadImage(thumbnail, "store/gallery");
            item.setThumbnailUrl(thumbnailUrl);
        }

        // Add new images if provided
        if (newImages != null && !newImages.isEmpty()) {
            int startOrder = item.getImages() != null ? item.getImages().size() : 0;
            for (int i = 0; i < newImages.size(); i++) {
                MultipartFile imageFile = newImages.get(i);
                if (imageFile != null && !imageFile.isEmpty()) {
                    String imageUrl = cloudinaryService.uploadImage(imageFile, "store/gallery/details");
                    GalleryImage galleryImage = GalleryImage.builder()
                            .galleryItem(item)
                            .imageUrl(imageUrl)
                            .sortOrder(startOrder + i)
                            .build();
                    item.getImages().add(galleryImage);
                }
            }
        }

        return galleryItemMapper.toResponse(galleryItemRepository.save(item));
    }

    @PreAuthorize("hasAuthority('GALLERY_DELETE')")
    public void deleteItem(Long id) {
        GalleryItem item = galleryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác phẩm gallery với ID: " + id));
        galleryItemRepository.delete(item);
    }

    @PreAuthorize("hasAuthority('GALLERY_DELETE')")
    public void deleteImage(Long itemId, Long imageId) {
        GalleryItem item = galleryItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác phẩm gallery với ID: " + itemId));

        item.getImages().removeIf(img -> img.getId().equals(imageId));
        galleryItemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public Page<GalleryItemResponse> getPublishedItems(Pageable pageable) {
        return galleryItemRepository.findPublishedItems(pageable)
                .map(galleryItemMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<GalleryItemResponse> getByCategory(Long categoryId, Pageable pageable) {
        GalleryCategory category = galleryCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục gallery với ID: " + categoryId));
        return galleryItemRepository.findPublishedByCategory(category, pageable)
                .map(galleryItemMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<GalleryItemResponse> searchPublished(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return galleryItemRepository.findPublishedItems(pageable)
                    .map(galleryItemMapper::toResponse);
        }
        return galleryItemRepository.findPublishedByKeyword(keyword.trim(), pageable)
                .map(galleryItemMapper::toResponse);
    }

    @Transactional
    public GalleryItemResponse getBySlugAndIncrementView(String slug) {
        GalleryItem item = galleryItemRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác phẩm với slug: " + slug));

        if (item.getPublished()) {
            galleryItemRepository.incrementViewCount(item.getId());
            item.setViewCount(item.getViewCount() + 1);
        }

        return galleryItemMapper.toResponse(item);
    }

    @Transactional(readOnly = true)
    public GalleryItemResponse getById(Long id) {
        GalleryItem item = galleryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác phẩm gallery với ID: " + id));
        return galleryItemMapper.toResponse(item);
    }

    @Transactional(readOnly = true)
    public Page<GalleryItemResponse> getAllItems(Pageable pageable) {
        return galleryItemRepository.findAll(pageable)
                .map(galleryItemMapper::toResponse);
    }

    @PreAuthorize("hasAuthority('GALLERY_TOGGLE_PUBLISH')")
    public void togglePublish(Long id) {
        GalleryItem item = galleryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tác phẩm gallery với ID: " + id));
        item.setPublished(!item.getPublished());
        galleryItemRepository.save(item);
    }
}
