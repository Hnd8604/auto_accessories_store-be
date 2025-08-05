package app.store.service.implementation;

import app.store.dto.request.ProductImageRequest;
import app.store.dto.response.ProductImageResponse;
import app.store.entity.Product;
import app.store.entity.ProductImage;
import app.store.mapper.ProductImageMapper;
import app.store.mapper.ProductMapper;
import app.store.repository.ProductImageRepository;
import app.store.repository.ProductRepository;
import app.store.service.interfaces.ProductImageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ProductImageServiceImpl implements ProductImageService {
    ProductImageRepository productImageRepository;
    ProductImageMapper productImageMapper;
    ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductImageResponse> getAllProductImages() {
        return productImageRepository.findAll().stream()
                .map(productImageMapper::toProductImageResponse).toList();
    }

    @Override
    public ProductImageResponse getProductImageById(Long id) {
        ProductImage productImage = productImageRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Product image not found with id: " + id));  ;
        return productImageMapper.toProductImageResponse(productImage);
    }

    @Override
    public List<ProductImageResponse> getProductImagesByProductId(Long productId) {
        return productImageRepository.getProductImageByProductId(productId).stream()
                .map(productImageMapper::toProductImageResponse).toList();
    }

    @Override
    public ProductImageResponse createProductImage(ProductImageRequest request) {
        ProductImage productImage = productImageMapper.toProductImage(request);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + request.getProductId()));
        productImage.setProduct(product);
        return productImageMapper.toProductImageResponse(
                productImageRepository.save(productImage));
    }

    @Override
    public ProductImageResponse updateProductImage(Long id, ProductImageRequest request) {
        // Tìm ProductImage cũ
        ProductImage productImage = productImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product image not found with id: " + id));
        
        // Tìm Product mới
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + request.getProductId()));
        
      productImageMapper.updateProductImage(productImage, request);
      productImage.setProduct(product);
        return productImageMapper.toProductImageResponse(
                productImageRepository.save(productImage));
    }

    @Override
    public void deleteProductImage(Long id) {
        ProductImage productImage = productImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product image not found with id: " + id));

        productImageRepository.delete(productImage);

    }
}
