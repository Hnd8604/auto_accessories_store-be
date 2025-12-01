package app.store.service.impl;

import app.store.dto.request.BrandRequest;
import app.store.dto.response.BrandResponse;
import app.store.entity.Brand;
import app.store.exception.AppException;
import app.store.exception.ErrorCode;
import app.store.mapper.BrandMapper;
import app.store.repository.BrandRepository;
import app.store.service.interfaces.BrandService;
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
public class BrandServiceImpl implements BrandService {
    BrandRepository brandRepository;
    BrandMapper brandMapper;
    @Override
    @PreAuthorize("hasAuthority('BRAND_GET_ALL')")
    public Page<BrandResponse> getAllBrands(Pageable pageable) {
        return brandRepository.findAll(pageable)
                .map(brandMapper::toBrandResponse);
    }
    @PreAuthorize("hasAuthority('BRAND_GET_BY_ID')")
    @Override
    public BrandResponse getBrandById(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        return brandMapper.toBrandResponse(brand);

    }

    @Override
    @PreAuthorize("hasAuthority('BRAND_CREATE')")
    public BrandResponse createBrand(BrandRequest brandRequest) {
        Brand brand = brandMapper.toBrand(brandRequest);
        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    @Override
    @PreAuthorize("hasAuthority('BRAND_UPDATE')")
    public BrandResponse updateBrand(Long brandId, BrandRequest brandRequest) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
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
