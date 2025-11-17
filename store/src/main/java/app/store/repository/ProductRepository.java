package app.store.repository;

import app.store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Hoặc sử dụng JPQL query
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    Page<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
    @Query("SELECT p FROM Product p WHERE p.brand.id = :brandId")
    Page<Product> findProductsByBrandId(@Param("brandId") Long brandId, Pageable pageable);

}
