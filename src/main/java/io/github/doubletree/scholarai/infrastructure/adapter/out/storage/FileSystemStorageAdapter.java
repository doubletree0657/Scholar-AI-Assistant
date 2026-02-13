package io.github.doubletree.scholarai.infrastructure.adapter.out.storage;

import io.github.doubletree.scholarai.domain.port.out.StoragePort;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Component
public class FileSystemStorageAdapter implements StoragePort {

    private final Path rootLocation;

    public FileSystemStorageAdapter(@Value("${scholar-ai.storage.upload-dir:./data/uploads}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir);
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
            log.info("📂 [Storage] Storage initialized at: {}", rootLocation.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    @Override
    public String save(InputStream inputStream, String originalFileName) throws IOException {
        // 生成唯一文件名防止冲突
        String fileExtension = getFileExtension(originalFileName);
        String storedFileName = UUID.randomUUID().toString() + fileExtension;

        Path destinationFile = this.rootLocation.resolve(Paths.get(storedFileName)).normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            throw new SecurityException("Cannot store file outside current directory.");
        }

        try (inputStream) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }

        log.debug("💾 [Storage] File stored: {} -> {}", originalFileName, storedFileName);
        return storedFileName;
    }

    @Override
    public InputStream load(String storedFileName) throws IOException {
        Path file = rootLocation.resolve(storedFileName);
        if (!Files.exists(file) || !Files.isReadable(file)) {
            throw new IOException("Could not read file: " + storedFileName);
        }
        return Files.newInputStream(file);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return ".pdf"; // 默认
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
