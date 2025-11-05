package app.store.service.interfaces;

import app.store.dto.request.BrandRequest;
import app.store.dto.response.BrandResponse;

import java.util.List;

public interface BrandService {
    List<BrandResponse> getAllBrands();
    BrandResponse getBrandById(Long id);
    BrandResponse createBrand(BrandRequest brandRequest);
    BrandResponse updateBrand(Long id, BrandRequest brandRequest);
    void deleteBrand(Long id);


}
