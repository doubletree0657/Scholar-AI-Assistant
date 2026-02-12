package io.github.doubletree.scholarai.domain.port.in;

import java.io.InputStream;
import io.github.doubletree.scholarai.domain.model.Paper;

public interface UploadPaperUseCase {
    Paper upload(UploadPaperCommand command);

    record UploadPaperCommand(
            InputStream fileStream,
            String fileName,
            long fileSize,
            String sourceUrl
    ) {
        public UploadPaperCommand {
            if (fileStream == null) {
                throw new IllegalArgumentException("File stream cannot be null");
            }
            if (fileName == null || fileName.isBlank()) {
                throw new IllegalArgumentException("File name cannot be blank");
            }
            if (fileSize <= 0) {
                throw new IllegalArgumentException("File size must be positive");
            }
        }
    }

    class PdfParsingException extends RuntimeException {
        public PdfParsingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
