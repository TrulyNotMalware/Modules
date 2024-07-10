package dev.notypie.infrastructure.dao.app.jpa;

import dev.notypie.domain.app.repository.AppRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "assistant.app.event.tracking", name = "type", havingValue = "jpa")
public class JpaConfiguration {

    /**
     * Creates a JpaAppRepository object.
     *
     * @param appRepository The RegisteredAppRepository object to be used by the JpaAppRepositoryImpl instance.
     * @return The JpaAppRepositoryImpl object.
     */
    @Bean
    public AppRepository jpaAppRepository(RegisteredAppRepository appRepository){
        return new JpaAppRepositoryImpl(appRepository);
    }
}
