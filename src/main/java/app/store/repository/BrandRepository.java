package app.store.repository;

import app.store.dto.response.BrandResponse;
import app.store.entity.Brand;
import app.store.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findBySlug(String slug);
    boolean existsBySlug(String slug);
    boolean existsByName(String name);
}
