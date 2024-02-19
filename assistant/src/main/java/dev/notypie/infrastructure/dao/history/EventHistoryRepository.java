package dev.notypie.infrastructure.dao.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Implementation Repository for History Domain
 */
@Repository
interface EventHistoryRepository extends JpaRepository<SlackCommandHistory, String> {

}
