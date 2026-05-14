package app.store.service;

import app.store.dto.request.GalleryCategoryRequest;
import app.store.dto.response.GalleryCategoryResponse;
import app.store.entity.GalleryCategory;
import app.store.mapper.GalleryCategoryMapper;
import app.store.repository.GalleryCategoryRepository;
import app.store.utils.SlugUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GalleryCategoryService {

    GalleryCategoryRepository galleryCategoryRepository;
    GalleryCategoryMapper galleryCategoryMapper;
    SlugUtil slugUtil;

    @PreAuthorize("hasAuthority('GALLERY_CREATE')")
    public GalleryCategoryResponse create(GalleryCategoryRequest request) {
        GalleryCategory category = galleryCategoryMapper.toEntity(request);

        String baseSlug = slugUtil.toSlug(request.getName());
        String uniqueSlug = slugUtil.createUniqueSlug(baseSlug, galleryCategoryRepository::existsBySlug);
        category.setSlug(uniqueSlug);

        if (request.getSortOrder() == null) {
            category.setSortOrder(0);
        }

        return galleryCategoryMapper.toResponse(galleryCategoryRepository.save(category));
    }

    @PreAuthorize("hasAuthority('GALLERY_UPDATE')")
    public GalleryCategoryResponse update(Long id, GalleryCategoryRequest request) {
        GalleryCategory category = galleryCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục gallery với ID: " + id));

        // Update slug if name changed
        if (!category.getName().equals(request.getName())) {
            String baseSlug = slugUtil.toSlug(request.getName());
            String uniqueSlug = slugUtil.createUniqueSlug(baseSlug, slug ->
                    !slug.equals(category.getSlug()) && galleryCategoryRepository.existsBySlug(slug));
            category.setSlug(uniqueSlug);
        }

        category.setName(request.getName());
        category.setDescription(request.getDescription());
        if (request.getSortOrder() != null) {
            category.setSortOrder(request.getSortOrder());
        }

        return galleryCategoryMapper.toResponse(galleryCategoryRepository.save(category));
    }

    @PreAuthorize("hasAuthority('GALLERY_DELETE')")
    public void delete(Long id) {
        GalleryCategory category = galleryCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục gallery với ID: " + id));
        galleryCategoryRepository.delete(category);
    }

    @Transactional(readOnly = true)
    public List<GalleryCategoryResponse> getAll() {
        return galleryCategoryRepository.findAll(Sort.by(Sort.Direction.ASC, "sortOrder"))
                .stream()
                .map(galleryCategoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GalleryCategoryResponse getById(Long id) {
        GalleryCategory category = galleryCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục gallery với ID: " + id));
        return galleryCategoryMapper.toResponse(category);
    }
}
