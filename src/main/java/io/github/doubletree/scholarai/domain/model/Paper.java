package io.github.doubletree.scholarai.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


public record Paper(
        PaperId id,
        String title,
        List<String> authors,
        String abstractText,
        String fullText,
        LocalDateTime publishedDate,
        String doi,
        PaperMetadata metadata
) {
    public Paper {
        Objects.requireNonNull(id, "Paper ID cannot be null");
        Objects.requireNonNull(title, "Title cannot be null");
        Objects.requireNonNull(authors, "Authors list cannot be null");
        
        if (title.isBlank()) throw new IllegalArgumentException("Title cannot be blank");
        if (authors.isEmpty()) throw new IllegalArgumentException("Paper must have at least one author");
        
        authors = List.copyOf(authors);
        if (metadata == null) metadata = PaperMetadata.createDefault();
    }

    public boolean hasDoi() {
        return doi != null && !doi.isBlank();
    }

    public boolean hasFullText() {
        return fullText != null && !fullText.isBlank();
    }

    public boolean isPublishedAfter(LocalDateTime date) {
        Objects.requireNonNull(date, "Date cannot be null");
        return publishedDate != null && publishedDate.isAfter(date);
    }

    public int getAuthorCount() {
        return authors.size();
    }

    public boolean isMultiAuthored() {
        return authors.size() > 1;
    }

    public String getCitationString() {
        String authorPart = authors.size() == 1 
            ? authors.get(0) 
            : authors.get(0) + " et al.";
        
        String yearPart = publishedDate != null 
            ? " (" + publishedDate.getYear() + ")" 
            : "";
        
        return authorPart + yearPart + ". " + title + ".";
    }

    public void validateEssentialContent() {
        if (!hasFullText() && (abstractText == null || abstractText.isBlank())) {
            throw new IllegalStateException("Paper must have either full text or an abstract");
        }
    }

    public static Paper create(
            String title,
            List<String> authors,
            String abstractText,
            String fullText,
            LocalDateTime publishedDate,
            String doi
    ) {
        return new Paper(
            PaperId.generate(),
            title,
            authors,
            abstractText,
            fullText,
            publishedDate,
            doi,
            PaperMetadata.createDefault()
        );
    }
}
