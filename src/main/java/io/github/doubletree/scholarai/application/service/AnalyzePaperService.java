package io.github.doubletree.scholarai.application.service;

import io.github.doubletree.scholarai.domain.model.PaperAnalysis;
import io.github.doubletree.scholarai.domain.model.PaperId;
import io.github.doubletree.scholarai.domain.port.in.AnalyzePaperUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyzePaperService implements AnalyzePaperUseCase {

    // private final SaveAnalysisPort saveAnalysisPort;

    @Override
    public PaperAnalysis analyze(PaperId paperId) {
        log.info("🚀 [Service] Starting analysis for paper: {}", paperId);

        throw new UnsupportedOperationException("分析功能尚未实现！");
    }
}
