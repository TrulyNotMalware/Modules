package dev.notypie.domain.history.repository;

import dev.notypie.domain.history.entity.History;

public interface HistoryRepository {

    History findByHistoryId(Long historyId);
    History save(History history);

}