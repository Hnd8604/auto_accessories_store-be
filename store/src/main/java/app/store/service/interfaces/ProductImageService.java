package app.store.service.interfaces;

import app.store.dto.request.ProductImageRequest;
import app.store.dto.request.ProductImageUpdateRequest;
import app.store.dto.response.ProductImageResponse;

import java.util.List;

public interface ProductImageService {
    List<ProductImageResponse> getAllProductImages();
    ProductImageResponse getProductImageById(Long id);
    List<ProductImageResponse> getProductImagesByProductId(Long productId);
    ProductImageResponse createProductImage(ProductImageRequest request);
    ProductImageResponse updateProductImage(Long id, ProductImageUpdateRequest request);
    void deleteProductImage(Long id);
    void setPrimaryImage(Long imageId, Long productId);
}
