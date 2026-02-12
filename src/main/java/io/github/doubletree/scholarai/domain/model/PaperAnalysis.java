package io.github.doubletree.scholarai.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public record PaperAnalysis(
        PaperId paperId,
        List<Citation> citations,
        List<TextChunk> chunks,
        List<Embedding> embeddings,
        LocalDateTime analyzedAt,
        AnalysisMetrics metrics
) {
    public PaperAnalysis {
        Objects.requireNonNull(paperId, "Paper ID cannot be null");
        Objects.requireNonNull(citations, "Citations list cannot be null");
        Objects.requireNonNull(chunks, "Chunks list cannot be null");
        Objects.requireNonNull(embeddings, "Embeddings list cannot be null");
        Objects.requireNonNull(metrics, "Metrics cannot be null");
        
        citations = List.copyOf(citations);
        chunks = List.copyOf(chunks);
        embeddings = List.copyOf(embeddings);
        
        if (analyzedAt == null) {
            analyzedAt = LocalDateTime.now();
        }
    }

    public int getCitationCount() {
        return citations.size();
    }

    public int getChunkCount() {
        return chunks.size();
    }

    public boolean hasCitations() {
        return !citations.isEmpty();
    }

    public void validateEmbeddingsMatchChunks() {
        if (embeddings.size() != chunks.size()) {
            throw new IllegalStateException(
                String.format("Embedding count (%d) does not match chunk count (%d)",
                    embeddings.size(), chunks.size())
            );
        }
    }

    public record AnalysisMetrics(
            long processingTimeMs,
            int totalTokens,
            double avgChunkSize,
            int uniqueCitations
    ) {
        public AnalysisMetrics {
            if (processingTimeMs < 0) {
                throw new IllegalArgumentException("Processing time cannot be negative");
            }
            if (totalTokens < 0) {
                throw new IllegalArgumentException("Total tokens cannot be negative");
            }
            if (avgChunkSize < 0) {
                throw new IllegalArgumentException("Average chunk size cannot be negative");
            }
            if (uniqueCitations < 0) {
                throw new IllegalArgumentException("Unique citations cannot be negative");
            }
        }
    }
}
