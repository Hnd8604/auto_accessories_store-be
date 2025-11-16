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
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        productRepository.delete(product);

    }
    @Transactional
    @Override
    public ProductResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        return productMapper.toProductResponse(product);
    }
    @Override
    public List<ProductResponse> getProductsByCategoryId(Long categoryId) {
        return productRepository.findProductsByCategoryId(categoryId).stream()
                .map(productMapper::toProductResponse)
                .toList();
    }
    @Override
    public List<ProductResponse> getProductsByBrandId(Long brandId) {
        return productRepository.findProductsByBrandId(brandId).stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toProductResponse);
    }
}
