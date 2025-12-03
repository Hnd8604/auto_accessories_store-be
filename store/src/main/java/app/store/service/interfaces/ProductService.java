package app.store.service.interfaces;

import app.store.dto.request.ProductRequest;
import app.store.dto.request.ProductSearchRequest;
import app.store.dto.response.ProductResponse;
import app.store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(Long productId, ProductRequest request);
    void deleteProduct(Long productId);
    ProductResponse getProductById(Long productId);
    ProductResponse getProductBySlug(String slug);
    Page<ProductResponse> getProductsByCategoryId(Long categoryId, Pageable pageable);
    Page<ProductResponse> getProductsByBrandId(Long brandId, Pageable pageable);
    Page<ProductResponse> getAllProducts(Pageable pageable);
    Page<ProductResponse> search(ProductSearchRequest req, Pageable pageable);
}
