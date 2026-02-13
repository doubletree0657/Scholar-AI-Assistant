package io.github.doubletree.scholarai.domain.port.out;

import java.util.Optional;
import io.github.doubletree.scholarai.domain.model.Paper;
import io.github.doubletree.scholarai.domain.model.PaperId;

public interface LoadPaperPort {
    Optional<Paper> load(PaperId paperId);
    
    Optional<Paper> loadByDoi(String doi);

    default Paper loadOrThrow(PaperId paperId) {
        return load(paperId).orElseThrow(() -> new PaperNotFoundException(paperId));
    }

    class PaperNotFoundException extends RuntimeException {
        public PaperNotFoundException(PaperId paperId) {
            super("Paper not found: " + paperId);
        }
    }
}
