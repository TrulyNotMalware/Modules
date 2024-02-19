package dev.notypie.aggregate.history.repository;

import dev.notypie.aggregate.history.entity.History;

public interface HistoryRepository {

    History findByHistoryId(Long historyId);
    History save(History history);

}