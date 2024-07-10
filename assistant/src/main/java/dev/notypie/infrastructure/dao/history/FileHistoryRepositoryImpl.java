package dev.notypie.infrastructure.dao.history;

import dev.notypie.domain.history.entity.History;
import dev.notypie.domain.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileHistoryRepositoryImpl implements HistoryRepository {

    @Override
    public History findByHistoryId(Long historyId) {
        return null;
    }

    @Override
    public History save(History history) {
        return null;
    }
}
