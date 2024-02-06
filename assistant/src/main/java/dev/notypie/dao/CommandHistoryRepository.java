package dev.notypie.dao;

import dev.notypie.aggregate.slack.domain.CommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandHistoryRepository extends JpaRepository<CommandHistory, Long> {

}
