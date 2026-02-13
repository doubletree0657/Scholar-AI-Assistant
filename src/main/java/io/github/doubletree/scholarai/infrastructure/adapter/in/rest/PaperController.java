package io.github.doubletree.scholarai.infrastructure.adapter.in.rest;

import io.github.doubletree.scholarai.application.service.DownloadPaperService;
import io.github.doubletree.scholarai.domain.model.Paper;
import io.github.doubletree.scholarai.domain.model.PaperId;
import io.github.doubletree.scholarai.domain.port.in.AnalyzePaperUseCase;
import io.github.doubletree.scholarai.domain.port.in.UploadPaperUseCase;
import io.github.doubletree.scholarai.domain.port.out.LoadPaperPort;
import io.github.doubletree.scholarai.infrastructure.adapter.in.rest.dto.PaperDto;
import io.github.doubletree.scholarai.infrastructure.adapter.in.rest.mapper.PaperMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/papers")
@RequiredArgsConstructor
@Slf4j
public class PaperController {

    private final LoadPaperPort loadPaperPort;
    private final UploadPaperUseCase uploadPaperUseCase;
    private final AnalyzePaperUseCase analyzePaperUseCase;
    private final DownloadPaperService downloadPaperService;
    private final PaperMapper paperMapper;

    // 获取列表 (简单实现，实际应该分页)
    @GetMapping
    public ResponseEntity<List<PaperDto>> getAllPapers() {
        // 这里需要 LoadPaperPort 增加 findAll 方法，暂时先返回空列表或模拟
        // 为了跑通前端，我们暂不实现 findAll 的数据库查询，留给你后续完善
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaperDto> getPaper(@PathVariable String id) {
        log.info("🌐 [REST] Get paper: {}", id);
        PaperId paperId = PaperId.fromString(id);
        Paper paper = loadPaperPort.loadOrThrow(paperId);
        return ResponseEntity.ok(paperMapper.toDto(paper));
    }

    @PostMapping("/upload")
    public ResponseEntity<PaperDto> uploadPaper(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("🌐 [REST] Uploading file: {}, size: {}", file.getOriginalFilename(), file.getSize());

        var command = new UploadPaperUseCase.UploadPaperCommand(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getSize(),
                null
        );

        Paper paper = uploadPaperUseCase.upload(command);
        log.info("✅ [REST] Upload success, id: {}", paper.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(paperMapper.toDto(paper));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadPaper(@PathVariable String id) {
        log.info("🌐 [REST] Download paper: {}", id);

        var inputStream = downloadPaperService.downloadPaper(id);
        var originalFileName = downloadPaperService.getOriginalFileName(id);

        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalFileName + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @PostMapping("/{id}/analyze")
    public ResponseEntity<String> analyzePaper(@PathVariable String id) {
        PaperId paperId = PaperId.fromString(id);
        analyzePaperUseCase.analyze(paperId);
        return ResponseEntity.ok("Analysis started");
    }

    @ExceptionHandler(LoadPaperPort.PaperNotFoundException.class)
    public ResponseEntity<String> handleNotFound(LoadPaperPort.PaperNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
