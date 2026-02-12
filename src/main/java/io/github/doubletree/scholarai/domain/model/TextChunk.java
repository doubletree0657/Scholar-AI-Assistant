package io.github.doubletree.scholarai.domain.model;

import java.util.Objects;

public record TextChunk(
        String content,
        int startPosition,
        int endPosition,
        int chunkIndex,
        ChunkMetadata metadata
) {
    public TextChunk {
        Objects.requireNonNull(content, "Content cannot be null");
        Objects.requireNonNull(metadata, "Metadata cannot be null");
        
        if (content.isBlank()) {
            throw new IllegalArgumentException("Content cannot be blank");
        }
        
        if (startPosition < 0) {
            throw new IllegalArgumentException("Start position cannot be negative");
        }
        
        if (endPosition <= startPosition) {
            throw new IllegalArgumentException("End position must be greater than start position");
        }
        
        if (chunkIndex < 0) {
            throw new IllegalArgumentException("Chunk index cannot be negative");
        }
    }

    public int getLength() {
        return content.length();
    }

    public boolean containsPosition(int position) {
        return position >= startPosition && position < endPosition;
    }

    public record ChunkMetadata(
            String section,
            int pageNumber,
            int paragraphNumber
    ) {
        public ChunkMetadata {
            if (pageNumber < 0) {
                throw new IllegalArgumentException("Page number cannot be negative");
            }
            if (paragraphNumber < 0) {
                throw new IllegalArgumentException("Paragraph number cannot be negative");
            }
        }
    }
}
