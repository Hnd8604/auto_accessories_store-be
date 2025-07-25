package app.store.service.interfaces;

import app.store.dto.request.ProductRequest;
import app.store.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(Long productId, ProductRequest request);
    ProductResponse deleteProduct(Long productId);
    ProductResponse getProduct(Long productId);
    List<ProductResponse> getProductsByCategoryId(Long categoryId);
    Page<ProductResponse> getAllProducts(Pageable pageable);

}
