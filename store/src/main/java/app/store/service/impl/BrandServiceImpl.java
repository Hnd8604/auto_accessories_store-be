package app.store.service.impl;

import app.store.dto.request.BrandRequest;
import app.store.dto.response.BrandResponse;
import app.store.entity.Brand;
import app.store.exception.AppException;
import app.store.exception.ErrorCode;
import app.store.mapper.BrandMapper;
import app.store.repository.BrandRepository;
import app.store.service.interfaces.BrandService;
import app.store.utils.SlugUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BrandServiceImpl implements BrandService {
    BrandRepository brandRepository;
    BrandMapper brandMapper;
    SlugUtil slugUtil;
    @Override
    @PreAuthorize("hasAuthority('BRAND_GET_ALL')")
    public List<BrandResponse> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toBrandResponse)
                .toList();
    }
    @PreAuthorize("hasAuthority('BRAND_GET_BY_ID')")
    @Override
    public BrandResponse getBrandById(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        return brandMapper.toBrandResponse(brand);

    }

    @Override
    public BrandResponse getBrandBySlug(String slug) {
        Brand brand = brandRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        return brandMapper.toBrandResponse(brand);

    }

    @Override
    @PreAuthorize("hasAuthority('BRAND_CREATE')")
    public BrandResponse createBrand(BrandRequest brandRequest) {

        Brand brand = brandMapper.toBrand(brandRequest);
        // Tạo slug từ tên danh mục
        String baseSlug = slugUtil.toSlug(brandRequest.getName());
        String uniqueSlug = slugUtil.createUniqueSlug(baseSlug, brandRepository::existsBySlug);

        brand.setSlug(uniqueSlug);
        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    @Override
    @PreAuthorize("hasAuthority('BRAND_UPDATE')")
    public BrandResponse updateBrand(Long brandId, BrandRequest brandRequest) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));

        // Cập nhật slug nếu tên thay đổi
        if (!brand.getName().equals(brandRequest.getName())) {
            String baseSlug = slugUtil.toSlug(brandRequest.getName());
            String uniqueSlug = slugUtil.createUniqueSlug(baseSlug, slug ->
                    !slug.equals(brand.getSlug()) && brandRepository.existsBySlug(slug));
            brand.setSlug(uniqueSlug);
        }
        brandMapper.updateBrand(brand, brandRequest);
        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    @Override
    @PreAuthorize("hasAuthority('BRAND_DELETE')")
    public void deleteBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        brandRepository.delete(brand);

    }
}
