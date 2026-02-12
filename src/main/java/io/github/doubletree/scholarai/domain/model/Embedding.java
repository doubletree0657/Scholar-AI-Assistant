package io.github.doubletree.scholarai.domain.model;

import java.util.Arrays;
import java.util.Objects;

public record Embedding(
        float[] vector,
        int dimensions,
        String model,
        int chunkIndex
) {
    public Embedding {
        Objects.requireNonNull(vector, "Vector cannot be null");
        Objects.requireNonNull(model, "Model cannot be null");
        
        if (vector.length == 0) {
            throw new IllegalArgumentException("Vector cannot be empty");
        }
        
        if (vector.length != dimensions) {
            throw new IllegalArgumentException(
                String.format("Vector length (%d) does not match dimensions (%d)",
                    vector.length, dimensions)
            );
        }
        
        if (chunkIndex < 0) {
            throw new IllegalArgumentException("Chunk index cannot be negative");
        }
        
        vector = Arrays.copyOf(vector, vector.length);
    }

    public double magnitude() {
        double sum = 0.0;
        for (float value : vector) {
            sum += value * value;
        }
        return Math.sqrt(sum);
    }

    public Embedding normalize() {
        double mag = magnitude();
        if (mag == 0.0) {
            return this;
        }
        
        float[] normalized = new float[vector.length];
        for (int i = 0; i < vector.length; i++) {
            normalized[i] = (float) (vector[i] / mag);
        }
        
        return new Embedding(normalized, dimensions, model, chunkIndex);
    }

    public double cosineSimilarity(Embedding other) {
        Objects.requireNonNull(other, "Other embedding cannot be null");
        
        if (this.dimensions != other.dimensions) {
            throw new IllegalArgumentException(
                "Cannot calculate similarity between embeddings of different dimensions"
            );
        }
        
        double dotProduct = 0.0;
        for (int i = 0; i < vector.length; i++) {
            dotProduct += vector[i] * other.vector[i];
        }
        
        return dotProduct / (this.magnitude() * other.magnitude());
    }

    public float[] getVectorCopy() {
        return Arrays.copyOf(vector, vector.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Embedding embedding = (Embedding) o;
        return dimensions == embedding.dimensions
            && chunkIndex == embedding.chunkIndex
            && Arrays.equals(vector, embedding.vector)
            && Objects.equals(model, embedding.model);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(dimensions, model, chunkIndex);
        result = 31 * result + Arrays.hashCode(vector);
        return result;
    }

    @Override
    public String toString() {
        return String.format("Embedding[dimensions=%d, model=%s, chunkIndex=%d, magnitude=%.4f]",
            dimensions, model, chunkIndex, magnitude());
    }
}
