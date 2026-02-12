package io.github.doubletree.scholarai.application.service;

import io.github.doubletree.scholarai.domain.model.Paper;
import io.github.doubletree.scholarai.domain.model.PaperId;
import io.github.doubletree.scholarai.domain.port.out.LoadPaperPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoadPaperService {
    private final LoadPaperPort loadPaperPort;

    public Paper load(PaperId paperId) {
        log.info("📋 [APP] Load paper: {}", paperId);
        return loadPaperPort.loadOrThrow(paperId);
    }

    public Optional<Paper> loadByDoi(String doi) {
        log.info("📋 [APP] Load by DOI: {}", doi);
        return loadPaperPort.loadByDoi(doi);
    }

    public boolean exists(PaperId paperId) {
        log.debug("📋 [APP] Check exists: {}", paperId);
        return loadPaperPort.load(paperId).isPresent();
    }
}
