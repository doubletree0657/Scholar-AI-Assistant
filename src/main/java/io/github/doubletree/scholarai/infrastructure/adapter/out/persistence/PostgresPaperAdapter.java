package io.github.doubletree.scholarai.infrastructure.adapter.out.persistence;

import io.github.doubletree.scholarai.domain.model.Paper;
import io.github.doubletree.scholarai.domain.model.PaperId;
import io.github.doubletree.scholarai.domain.port.out.LoadPaperPort;
import io.github.doubletree.scholarai.domain.port.out.SavePaperPort;
import io.github.doubletree.scholarai.infrastructure.adapter.out.persistence.entity.PaperEntity;
import io.github.doubletree.scholarai.infrastructure.adapter.out.persistence.mapper.PaperEntityMapper;
import io.github.doubletree.scholarai.infrastructure.adapter.out.persistence.repository.PaperJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostgresPaperAdapter implements LoadPaperPort, SavePaperPort {
    private final PaperJpaRepository jpaRepository;
    private final PaperEntityMapper entityMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Paper> load(PaperId paperId) {
        log.debug("💾 [DB] Query: id={}", paperId);
        
        Optional<PaperEntity> entityOptional = jpaRepository.findById(paperId.value());
        
        Optional<Paper> paperOptional = entityOptional.map(entity -> {
            Paper paper = entityMapper.toDomain(entity);
            log.debug("✅ [DB] Found: {}", paper.title());
            return paper;
        });
        
        if (paperOptional.isEmpty()) {
            log.warn("⚠️ [DB] Not found: id={}", paperId);
        }
        
        return paperOptional;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Paper> loadByDoi(String doi) {
        log.debug("💾 [DB] Query by DOI: {}", doi);
        
        return jpaRepository.findByDoi(doi)
            .map(entity -> {
                Paper paper = entityMapper.toDomain(entity);
                log.debug("✅ [DB] Found by DOI: {}", paper.title());
                return paper;
            });
    }

    @Override
    @Transactional
    public Paper save(Paper paper) {
        log.info("💾 [DB] Save: id={}, title={}", paper.id(), paper.title());
        
        PaperEntity entity = entityMapper.toEntity(paper);
        PaperEntity savedEntity = jpaRepository.save(entity);
        
        log.info("✅ [DB] Saved: id={}", savedEntity.getId());
        
        return entityMapper.toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(PaperId paperId) {
        boolean exists = jpaRepository.existsById(paperId.value());
        log.debug("💾 [DB] Exists check: id={}, result={}", paperId, exists);
        return exists;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByDoi(String doi) {
        boolean exists = jpaRepository.existsByDoi(doi);
        log.debug("💾 [DB] Exists by DOI: doi={}, result={}", doi, exists);
        return exists;
    }
}
