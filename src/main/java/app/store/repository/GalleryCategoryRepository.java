package app.store.repository;

import app.store.entity.GalleryCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GalleryCategoryRepository extends JpaRepository<GalleryCategory, Long> {

    Optional<GalleryCategory> findBySlug(String slug);

    boolean existsBySlug(String slug);

    boolean existsByName(String name);
}
