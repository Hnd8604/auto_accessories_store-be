package app.store.service.implementation;

import app.store.dto.request.ProductRequest;
import app.store.dto.response.ProductResponse;
import app.store.entity.Brand;
import app.store.entity.Category;
import app.store.entity.Product;
import app.store.exception.AppException;
import app.store.exception.ErrorCode;
import app.store.mapper.ProductMapper;
import app.store.repository.BrandRepository;
import app.store.repository.CategoryRepository;
import app.store.repository.ProductRepository;
import app.store.service.interfaces.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductServiceImpl implements ProductService {
    ProductMapper productMapper;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    BrandRepository brandRepository;
    @Override
    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    public ProductResponse createProduct(ProductRequest request) {
        Product product = productMapper.toProduct(request);
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));

        product.setCategory(category);
        product.setBrand(brand);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Transactional
    @Override
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

                productMapper.updateProduct(product, request);
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));

        product.setCategory(category);
        product.setBrand(brand);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Transactional
    @Override
    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        productRepository.delete(product);

    }
    @Transactional
    @Override
    @PreAuthorize("hasAuthority('PRODUCT_GET_BY_ID')")
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        return productMapper.toProductResponse(product);
    }
    @Override
    @PreAuthorize("hasAuthority('PRODUCT_GET_BY_CATEGORY_ID')")
    public Page<ProductResponse> getProductsByCategoryId(Long categoryId, Pageable pageable) {
        return productRepository.findProductsByCategoryId(categoryId, pageable)
                .map(productMapper::toProductResponse);
    }
    @Override
    @PreAuthorize("hasAuthority('PRODUCT_GET_BY_BRAND_ID')")
    public Page<ProductResponse> getProductsByBrandId(Long brandId, Pageable pageable) {
        return productRepository.findProductsByBrandId(brandId, pageable)
                .map(productMapper::toProductResponse);
    }

    @Override
    @PreAuthorize("hasAuthority('PRODUCT_GET_ALL')")
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toProductResponse);
    }
}
