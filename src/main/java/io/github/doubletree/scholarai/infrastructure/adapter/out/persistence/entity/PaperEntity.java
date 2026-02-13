package io.github.doubletree.scholarai.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "paper", indexes = {
    @Index(name = "idx_papers_doi", columnList = "doi"),
    @Index(name = "idx_papers_title", columnList = "title")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaperEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "authors", columnDefinition = "text[]")
    private String[] authors;

    @Column(name = "abstract_text", columnDefinition = "TEXT")
    private String abstractText;

    @Column(name = "full_text", columnDefinition = "TEXT")
    private String fullText;

    @Column(name = "doi", length = 100)
    private String doi;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @Column(name = "uploaded_at", nullable = false)
    private LocalDateTime uploadedAt;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "status", length = 50)
    @Enumerated(EnumType.STRING)
    private ProcessingStatus status;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Long fileSize;

    public enum ProcessingStatus {
        PENDING, PROCESSING, COMPLETED, FAILED
    }

    @PrePersist
    protected void onCreate() {
        if (uploadedAt == null) uploadedAt = LocalDateTime.now();
        if (status == null) status = ProcessingStatus.PENDING;
    }
}
