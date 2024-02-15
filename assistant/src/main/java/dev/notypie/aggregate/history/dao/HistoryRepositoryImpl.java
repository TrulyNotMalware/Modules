package dev.notypie.aggregate.history.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoryRepositoryImpl implements HistoryRepository{
    private final EventHistoryRepository eventHistoryRepository;
    private final CommandHistoryRepository commandHistoryRepository;

}
