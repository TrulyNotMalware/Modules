package dev.notypie.infrastructure.dao.app.jpa;

import dev.notypie.aggregate.app.repository.AppRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class JpaConfiguration {

    /**
     * Creates a new instance of AppRepository based on the input RegisteredAppRepository.
     * @param RegisteredAppRepository the RegisteredAppRepository used to create the AppRepository
     * @return a new instance of AppRepository
     */
    @Bean
    @ConditionalOnProperty(prefix = "assistant.app.event.tracking", name = "type", havingValue = "jpa")
    public AppRepository jpaAppRepository(RegisteredAppRepository appRepository){
        return new JpaAppRepositoryImpl(appRepository);
    }
}
