package app.store.service.interfaces;

import app.store.dto.request.ProductImageRequest;
import app.store.dto.request.ProductImageUpdateRequest;
import app.store.dto.response.ProductImageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {
    Page<ProductImageResponse> getAllProductImages(Pageable pageable);
    ProductImageResponse getProductImageById(Long imageId);
    List<ProductImageResponse> getProductImagesByProductId(Long productId);
    ProductImageResponse createProductImage(MultipartFile file, ProductImageRequest request);
    ProductImageResponse updateProductImage(Long imageId, ProductImageUpdateRequest request);
    void deleteProductImage(Long imageId);
    void setPrimaryImage(Long imageId, Long productId);
}
