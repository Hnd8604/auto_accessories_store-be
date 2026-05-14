package app.store.repository;

import app.store.entity.GalleryCategory;
import app.store.entity.GalleryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GalleryItemRepository extends JpaRepository<GalleryItem, Long> {

    Optional<GalleryItem> findBySlug(String slug);

    boolean existsBySlug(String slug);

    @Query("SELECT g FROM GalleryItem g WHERE g.published = true")
    Page<GalleryItem> findPublishedItems(Pageable pageable);

    @Query("SELECT g FROM GalleryItem g WHERE g.category = :category AND g.published = true")
    Page<GalleryItem> findPublishedByCategory(@Param("category") GalleryCategory category, Pageable pageable);

    @Query("SELECT g FROM GalleryItem g WHERE g.published = true AND " +
           "(LOWER(g.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(g.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(g.carModel) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<GalleryItem> findPublishedByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Modifying
    @Query("UPDATE GalleryItem g SET g.viewCount = g.viewCount + 1 WHERE g.id = :itemId")
    void incrementViewCount(@Param("itemId") Long itemId);

    @Query("SELECT COUNT(g) FROM GalleryItem g WHERE g.published = true")
    Long countPublishedItems();
}
