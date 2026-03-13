package app.store.repository;

import app.store.entity.Post;
import app.store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    // Hoặc sử dụng JPQL query
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    Page<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
    @Query("SELECT p FROM Product p WHERE p.brand.id = :brandId")
    Page<Product> findProductsByBrandId(@Param("brandId") Long brandId, Pageable pageable);

    boolean existsBySlug(String slug);
    Optional<Product> findBySlug(String slug);
//    @Query("""
//        SELECT p FROM Product p
//        WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
//           OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
//    """)
//    Page<Product> searchByKeyword(String keyword, Pageable pageable);
}
