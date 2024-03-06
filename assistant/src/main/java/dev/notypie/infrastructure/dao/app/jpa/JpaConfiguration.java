package dev.notypie.infrastructure.dao.app.jpa;

import dev.notypie.aggregate.app.repository.AppRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "assistant.app.event.tracking", name = "type", havingValue = "jpa")
public class JpaConfiguration {

    /**
     * Creates a new instance of AppRepository based on the input RegisteredAppRepository.
     * @param RegisteredAppRepository the RegisteredAppRepository used to create the AppRepository
     * @return a new instance of AppRepository
     */
    @Bean
    public AppRepository jpaAppRepository(RegisteredAppRepository appRepository){
        return new JpaAppRepositoryImpl(appRepository);
    }
}
