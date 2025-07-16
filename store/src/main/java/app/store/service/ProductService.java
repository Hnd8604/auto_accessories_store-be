package app.store.service;

import app.store.dto.request.ProductCreationRequest;
import app.store.dto.request.ProductUpdateRequest;
import app.store.dto.response.ProductResponse;
import app.store.entity.Product;
import app.store.mapper.ProductMapper;
import app.store.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductService {
    ProductMapper productMapper;
    ProductRepository productRepository;

    public ProductResponse createProduct(ProductCreationRequest request) {
        Product product = productMapper.toProduct(request);

        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse updateProduct(String productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new RuntimeException("Product not found"));

                productMapper.updateProduct(product, request);
        return productMapper.toProductResponse(productRepository.save(product));
    }
    @Transactional
    public ProductResponse deleteProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);
        return productMapper.toProductResponse(product);
    }
    @Transactional
    public ProductResponse getProduct(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return productMapper.toProductResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponse)
                .toList();
    }


}
