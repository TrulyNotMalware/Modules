package dev.notypie.aggregate.history.dao;

import dev.notypie.aggregate.history.domain.CommandHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CommandHistoryRepository extends JpaRepository<CommandHistory, Long> {

}
