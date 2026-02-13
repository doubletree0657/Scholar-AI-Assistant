package io.github.doubletree.scholarai.infrastructure.adapter.out.persistence.mapper;

import io.github.doubletree.scholarai.domain.model.Paper;
import io.github.doubletree.scholarai.domain.model.PaperId;
import io.github.doubletree.scholarai.domain.model.PaperMetadata;
import io.github.doubletree.scholarai.infrastructure.adapter.out.persistence.entity.PaperEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PaperEntityMapper {
    public Paper toDomain(PaperEntity entity) {
        if (entity == null) return null;

        List<String> authors = entity.getAuthors() != null 
            ? Arrays.asList(entity.getAuthors())
            : List.of();

        PaperMetadata metadata = new PaperMetadata(
            null,
            entity.getFileName(),
            entity.getFileSize() != null ? entity.getFileSize() : 0L,
            "application/pdf",
            entity.getUploadedAt(),
            entity.getProcessedAt(),
            mapStatus(entity.getStatus()),
            java.util.Map.of()
        );

        return new Paper(
            new PaperId(entity.getId()),
            entity.getTitle(),
            authors,
            entity.getAbstractText(),
            entity.getFullText(),
            entity.getPublishedDate(),
            entity.getDoi(),
            metadata
        );
    }

    public PaperEntity toEntity(Paper paper) {
        if (paper == null) return null;

        String[] authorsArray = paper.authors().toArray(new String[0]);

        return PaperEntity.builder()
            .id(paper.id().value())
            .title(paper.title())
            .authors(authorsArray)
            .abstractText(paper.abstractText())
            .fullText(paper.fullText())
            .doi(paper.doi())
            .publishedDate(paper.publishedDate())
            .uploadedAt(paper.metadata().uploadedAt())
            .processedAt(paper.metadata().processedAt())
            .status(mapStatus(paper.metadata().status()))
            .fileName(paper.metadata().fileName())
            .fileSize(paper.metadata().fileSize())
            .build();
    }

    private PaperEntity.ProcessingStatus mapStatus(PaperMetadata.ProcessingStatus domainStatus) {
        if (domainStatus == null) return PaperEntity.ProcessingStatus.PENDING;
        return switch (domainStatus) {
            case PENDING -> PaperEntity.ProcessingStatus.PENDING;
            case PROCESSING -> PaperEntity.ProcessingStatus.PROCESSING;
            case COMPLETED -> PaperEntity.ProcessingStatus.COMPLETED;
            case FAILED -> PaperEntity.ProcessingStatus.FAILED;
        };
    }

    private PaperMetadata.ProcessingStatus mapStatus(PaperEntity.ProcessingStatus entityStatus) {
        if (entityStatus == null) return PaperMetadata.ProcessingStatus.PENDING;
        return switch (entityStatus) {
            case PENDING -> PaperMetadata.ProcessingStatus.PENDING;
            case PROCESSING -> PaperMetadata.ProcessingStatus.PROCESSING;
            case COMPLETED -> PaperMetadata.ProcessingStatus.COMPLETED;
            case FAILED -> PaperMetadata.ProcessingStatus.FAILED;
        };
    }
}
