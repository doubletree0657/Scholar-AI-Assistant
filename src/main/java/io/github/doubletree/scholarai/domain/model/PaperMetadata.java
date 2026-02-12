package io.github.doubletree.scholarai.domain.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public record PaperMetadata(
        String sourceUrl,
        String fileName,
        long fileSize,
        String fileType,
        LocalDateTime uploadedAt,
        LocalDateTime processedAt,
        ProcessingStatus status,
        Map<String, String> additionalProperties
) {
    public PaperMetadata {
        Objects.requireNonNull(status, "Processing status cannot be null");
        
        if (fileSize < 0) {
            throw new IllegalArgumentException("File size cannot be negative");
        }
        
        if (additionalProperties != null) {
            additionalProperties = Map.copyOf(additionalProperties);
        } else {
            additionalProperties = Map.of();
        }
    }

    public static PaperMetadata createDefault() {
        return new PaperMetadata(
            null,
            null,
            0L,
            "application/pdf",
            LocalDateTime.now(),
            null,
            ProcessingStatus.PENDING,
            Map.of()
        );
    }

    public PaperMetadata withStatus(ProcessingStatus newStatus) {
        return new PaperMetadata(
            sourceUrl,
            fileName,
            fileSize,
            fileType,
            uploadedAt,
            LocalDateTime.now(),
            newStatus,
            additionalProperties
        );
    }

    public boolean isProcessed() {
        return status == ProcessingStatus.COMPLETED;
    }

    public boolean hasFailed() {
        return status == ProcessingStatus.FAILED;
    }

    public enum ProcessingStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED
    }
}
