package app.store.repository;

import app.store.entity.Order;
import app.store.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    @Query("SELECT p FROM ProductImage p WHERE p.product.id = :productId")
    List<ProductImage> getProductImageByProductId(@Param("productId") Long productId);
}
