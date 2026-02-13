package io.github.doubletree.scholarai.domain.port.out;

import io.github.doubletree.scholarai.domain.model.Paper;
import io.github.doubletree.scholarai.domain.model.PaperId;

public interface SavePaperPort {
    Paper save(Paper paper);
    
    boolean exists(PaperId paperId);
    
    boolean existsByDoi(String doi);

    class PersistenceException extends RuntimeException {
        public PersistenceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
