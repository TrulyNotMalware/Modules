package dev.notypie.infrastructure.dao.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SlackCommandHistoryRepository extends JpaRepository<SlackCommandHistory, Long> {

}
