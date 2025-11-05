package app.store.repository;

import app.store.dto.response.BrandResponse;
import app.store.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByName(String name);
}
