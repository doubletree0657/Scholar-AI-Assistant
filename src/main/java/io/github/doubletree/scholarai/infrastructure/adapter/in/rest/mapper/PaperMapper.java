package io.github.doubletree.scholarai.infrastructure.adapter.in.rest.mapper;

import io.github.doubletree.scholarai.domain.model.Paper;
import io.github.doubletree.scholarai.infrastructure.adapter.in.rest.dto.PaperDto;
import org.springframework.stereotype.Component;

@Component
public class PaperMapper {
    public PaperDto toDto(Paper paper) {
        if (paper == null) return null;
        
        return new PaperDto(
            paper.id().toString(),
            paper.title(),
            paper.authors(),
            paper.abstractText(),
            paper.doi(),
            paper.publishedDate(),
            paper.metadata().uploadedAt()
        );
    }

    public Paper toDomain(PaperDto dto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
