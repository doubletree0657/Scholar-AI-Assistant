package io.github.doubletree.scholarai.infrastructure.adapter.in.rest;

import io.github.doubletree.scholarai.domain.model.Paper;
import io.github.doubletree.scholarai.domain.model.PaperId;
import io.github.doubletree.scholarai.domain.port.in.AnalyzePaperUseCase;
import io.github.doubletree.scholarai.domain.port.out.LoadPaperPort;
import io.github.doubletree.scholarai.infrastructure.adapter.in.rest.dto.PaperDto;
import io.github.doubletree.scholarai.infrastructure.adapter.in.rest.mapper.PaperMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/papers")
@RequiredArgsConstructor
@Slf4j
public class PaperController {
    private final LoadPaperPort loadPaperPort;
    private final AnalyzePaperUseCase analyzePaperUseCase;
    private final PaperMapper paperMapper;

    @GetMapping("/{id}")
    public ResponseEntity<PaperDto> getPaper(@PathVariable String id) {
        log.info("🌐 [REST] Get paper: {}", id);
        
        PaperId paperId = PaperId.fromString(id);
        Paper paper = loadPaperPort.loadOrThrow(paperId);
        
        log.info("✅ [REST] Found: {}", paper.title());
        
        PaperDto dto = paperMapper.toDto(paper);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}/analyze")
    public ResponseEntity<String> analyzePaper(@PathVariable String id) {
        log.info("🌐 [REST] Analyze paper: {}", id);
        
        PaperId paperId = PaperId.fromString(id);
        var analysis = analyzePaperUseCase.analyze(paperId);
        
        log.info("✅ [REST] Analysis done: citations={}, chunks={}", 
            analysis.getCitationCount(), 
            analysis.getChunkCount()
        );
        
        return ResponseEntity.ok("Analysis completed");
    }

    @ExceptionHandler(LoadPaperPort.PaperNotFoundException.class)
    public ResponseEntity<String> handleNotFound(LoadPaperPort.PaperNotFoundException ex) {
        log.warn("⚠️ [REST] Not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
