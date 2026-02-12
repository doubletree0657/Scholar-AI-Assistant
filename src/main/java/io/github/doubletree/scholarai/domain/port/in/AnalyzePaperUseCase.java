package io.github.doubletree.scholarai.domain.port.in;

import io.github.doubletree.scholarai.domain.model.PaperAnalysis;
import io.github.doubletree.scholarai.domain.model.PaperId;

public interface AnalyzePaperUseCase {
    PaperAnalysis analyze(PaperId paperId);

    class PaperNotFoundException extends RuntimeException {
        public PaperNotFoundException(PaperId paperId) {
            super("Paper not found: " + paperId);
        }
    }

    class AnalysisFailedException extends RuntimeException {
        public AnalysisFailedException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
