package io.github.doubletree.scholarai.domain.model;

import java.util.Objects;
import java.util.UUID;

public record PaperId(UUID value) {
    public PaperId {
        Objects.requireNonNull(value, "Paper ID value cannot be null");
    }

    public static PaperId fromString(String value) {
        Objects.requireNonNull(value, "Paper ID string cannot be null");
        return new PaperId(UUID.fromString(value));
    }

    public static PaperId generate() {
        return new PaperId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
