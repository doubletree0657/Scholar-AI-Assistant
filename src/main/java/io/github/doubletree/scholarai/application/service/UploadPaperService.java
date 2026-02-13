package io.github.doubletree.scholarai.application.service;

import io.github.doubletree.scholarai.domain.model.Paper;
import io.github.doubletree.scholarai.domain.model.PaperMetadata;
import io.github.doubletree.scholarai.domain.port.in.UploadPaperUseCase;
import io.github.doubletree.scholarai.domain.port.out.SavePaperPort;
import io.github.doubletree.scholarai.domain.port.out.StoragePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadPaperService implements UploadPaperUseCase {

    private final StoragePort storagePort;
    private final SavePaperPort savePaperPort;

    @Override
    @Transactional
    public Paper upload(UploadPaperCommand command) {
        log.info("🚀 [Service] Starting upload: {}", command.fileName());

        try {
            // 1. 保存物理文件
            String storedFileName = storagePort.save(command.fileStream(), command.fileName());

            // 2. 创建领域对象 (初始状态，尚未解析内容)
            // 注意：因为还没有解析 PDF，我们暂时把 content 设为 null，authors 设为 "Unknown"
            // 解析逻辑将在后续步骤通过事件或显式调用触发
            Paper paper = Paper.create(
                    removeExtension(command.fileName()), // 临时标题设为文件名
                    List.of("Unknown"),                  // 临时作者
                    null,                                // 摘要暂空
                    null,                                // 全文暂空
                    null,                                // 发布日期暂空
                    null                                 // DOI 暂空
            );

            // 3. 更新元数据（关联存储的文件名）
            PaperMetadata metadata = new PaperMetadata(
                    command.sourceUrl(),
                    storedFileName, // 存储的是 UUID 文件名，而非原始文件名
                    command.fileSize(),
                    "application/pdf",
                    paper.metadata().uploadedAt(),
                    null,
                    PaperMetadata.ProcessingStatus.PENDING,
                    Map.of("originalFileName", command.fileName()) // 记录原始文件名
            );

            Paper paperWithMetadata = new Paper(
                    paper.id(),
                    paper.title(),
                    paper.authors(),
                    paper.abstractText(),
                    paper.fullText(),
                    paper.publishedDate(),
                    paper.doi(),
                    metadata
            );

            // 4. 保存到数据库
            return savePaperPort.save(paperWithMetadata);

        } catch (IOException e) {
            log.error("❌ Upload failed", e);
            throw new RuntimeException("Failed to store file", e);
        }
    }

    private String removeExtension(String fileName) {
        if (fileName != null && fileName.lastIndexOf(".") > 0) {
            return fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }
}
