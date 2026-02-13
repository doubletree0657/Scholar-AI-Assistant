package io.github.doubletree.scholarai.infrastructure.adapter.out.persistence.repository;

import io.github.doubletree.scholarai.infrastructure.adapter.out.persistence.entity.PaperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaperJpaRepository extends JpaRepository<PaperEntity, UUID> {
    Optional<PaperEntity> findByDoi(String doi);
    
    boolean existsByDoi(String doi);

    @Query("SELECT p FROM PaperEntity p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Optional<PaperEntity> findByTitleContainingIgnoreCase(@Param("keyword") String keyword);

    @Query("SELECT p FROM PaperEntity p WHERE p.status = 'COMPLETED' AND p.processedAt IS NOT NULL")
    java.util.List<PaperEntity> findAllProcessed();
}
