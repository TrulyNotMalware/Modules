package dev.notypie.aggregate.history.configurations;

import dev.notypie.aggregate.history.dao.HistoryRepository;
import dev.notypie.aggregate.history.dao.HistoryRepositoryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HistoryConfiguration {

//    @Bean
////    @ConditionalOnProperty(prefix = "event.logs", name = "enabled", havingValue = "true", matchIfMissing = false)
//    @ConditionalOnExpression(
//            "${event.logs.enabled:true} and ${event.logs.type} == jpa"
//    )
//    public HistoryRepository historyRepository(){
//        return new HistoryRepositoryImpl();
//    }
}
