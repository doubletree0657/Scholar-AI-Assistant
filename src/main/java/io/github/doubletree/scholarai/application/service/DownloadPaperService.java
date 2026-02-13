package io.github.doubletree.scholarai.application.service;

import io.github.doubletree.scholarai.domain.model.Paper;
import io.github.doubletree.scholarai.domain.model.PaperId;
import io.github.doubletree.scholarai.domain.port.out.LoadPaperPort;
import io.github.doubletree.scholarai.domain.port.out.StoragePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DownloadPaperService {
    private final LoadPaperPort loadPaperPort;
    private final StoragePort storagePort;

    public InputStream downloadPaper(String paperIdStr) {
        PaperId paperId = PaperId.fromString(paperIdStr);
        Paper paper = loadPaperPort.loadOrThrow(paperId);

        String storedFileName = paper.metadata().fileName();
        if (storedFileName == null) {
            throw new IllegalStateException("Paper has no file associated");
        }

        try {
            return storagePort.load(storedFileName);
        } catch (Exception e) {
            log.error("Failed to load file for paper {}", paperId, e);
            throw new RuntimeException("Could not read file storage", e);
        }
    }

    public String getOriginalFileName(String paperIdStr) {
        PaperId paperId = PaperId.fromString(paperIdStr);
        Paper paper = loadPaperPort.loadOrThrow(paperId);
        return paper.metadata().additionalProperties().getOrDefault("originalFileName", "download.pdf");
    }
}
