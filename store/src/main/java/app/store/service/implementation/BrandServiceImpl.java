package app.store.service.implementation;

import app.store.dto.request.BrandRequest;
import app.store.dto.response.BrandResponse;
import app.store.entity.Brand;
import app.store.mapper.BrandMapper;
import app.store.repository.BrandRepository;
import app.store.service.interfaces.BrandService;
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
public class BrandServiceImpl implements BrandService {
    BrandRepository brandRepository;
    BrandMapper brandMapper;
    @Override
    public List<BrandResponse> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(brandMapper::toBrandResponse).toList();
    }

    @Override
    public BrandResponse getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
        return brandMapper.toBrandResponse(brand);

    }

    @Override
    public BrandResponse createBrand(BrandRequest brandRequest) {
        Brand brand = brandMapper.toBrand(brandRequest);
        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    @Override
    public BrandResponse updateBrand(Long id, BrandRequest brandRequest) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
        brandMapper.updateBrand(brand, brandRequest);
        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

    @Override
    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + id));
        brandRepository.delete(brand);

    }
}
