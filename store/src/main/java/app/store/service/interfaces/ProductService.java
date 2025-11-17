package app.store.service.interfaces;

import app.store.dto.request.ProductRequest;
import app.store.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(Long productId, ProductRequest request);
    void deleteProduct(Long productId);
    ProductResponse getProduct(Long productId);
    Page<ProductResponse> getProductsByCategoryId(Long categoryId, Pageable pageable);
    Page<ProductResponse> getProductsByBrandId(Long brandId, Pageable pageable);
    Page<ProductResponse> getAllProducts(Pageable pageable);

}
