package io.github.doubletree.scholarai.infrastructure.adapter.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public record PaperDto(
        @JsonProperty("id") String id,
        @JsonProperty("title") String title,
        @JsonProperty("authors") List<String> authors,
        @JsonProperty("abstract") String abstractText,
        @JsonProperty("doi") String doi,
        @JsonProperty("published_date") LocalDateTime publishedDate,
        @JsonProperty("uploaded_at") LocalDateTime uploadedAt
) {}
