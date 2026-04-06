package app.store.repository;

import app.store.entity.GalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryImageRepository extends JpaRepository<GalleryImage, Long> {

    List<GalleryImage> findByGalleryItemIdOrderBySortOrderAsc(Long galleryItemId);

    void deleteByGalleryItemId(Long galleryItemId);
}
