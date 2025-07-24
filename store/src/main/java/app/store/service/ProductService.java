package app.store.service;

import app.store.dto.request.ProductRequest;
import app.store.dto.response.ProductResponse;
import app.store.entity.Category;
import app.store.entity.Product;
import app.store.exception.AppException;
import app.store.exception.ErrorCode;
import app.store.mapper.ProductMapper;
import app.store.repository.CategoryRepository;
import app.store.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductService {
    ProductMapper productMapper;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public ProductResponse createProduct(ProductRequest request) {
        Product product = productMapper.toProduct(request);
        Category category = categoryRepository.findByCategoryName(request.getCategoryName());
        product.setCategory(category);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

                productMapper.updateProduct(product, request);
        return productMapper.toProductResponse(productRepository.save(product));
    }
    @Transactional
    public ProductResponse deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        productRepository.delete(product);
        return productMapper.toProductResponse(product);
    }
    @Transactional
    public ProductResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        return productMapper.toProductResponse(product);
    }

    public List<ProductResponse> getProductsByCategoryId(Long categoryId) {

        return productRepository.findProductsByCategoryId(categoryId).stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponse)
                .toList();
    }


}
