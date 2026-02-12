package io.github.doubletree.scholarai.domain.model;

import java.util.List;
import java.util.Objects;

public record Citation(
        String citationText,
        List<String> authors,
        Integer year,
        String title,
        String venue,
        String doi,
        int pageNumber,
        CitationType type
) {
    public Citation {
        Objects.requireNonNull(citationText, "Citation text cannot be null");
        Objects.requireNonNull(type, "Citation type cannot be null");
        
        if (citationText.isBlank()) {
            throw new IllegalArgumentException("Citation text cannot be blank");
        }
        
        if (authors != null) {
            authors = List.copyOf(authors);
        } else {
            authors = List.of();
        }
    }

    public boolean isComplete() {
        return !authors.isEmpty() && year != null && title != null && !title.isBlank();
    }

    public boolean hasDoi() {
        return doi != null && !doi.isBlank();
    }

    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        
        if (!authors.isEmpty()) {
            if (authors.size() == 1) {
                sb.append(authors.get(0));
            } else {
                sb.append(authors.get(0)).append(" et al.");
            }
        }
        
        if (year != null) {
            sb.append(" (").append(year).append(")");
        }
        
        if (title != null) {
            sb.append(". ").append(title);
        }
        
        if (venue != null) {
            sb.append(". ").append(venue);
        }
        
        return sb.toString();
    }

    public enum CitationType {
        IN_TEXT,
        FOOTNOTE,
        BIBLIOGRAPHY,
        REFERENCE_LIST
    }
}
