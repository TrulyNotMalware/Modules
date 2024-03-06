package dev.notypie.infrastructure.dao.app.file;

import dev.notypie.aggregate.app.repository.AppRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(AppRepository.class)
public class FileConfiguration {

    @Bean
    public AppRepository appRepository(){
        return new FileAppRepositoryImpl();
    }
}
