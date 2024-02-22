package dev.notypie.infrastructure.dao.app.file;

import dev.notypie.aggregate.app.repository.AppRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FileConfiguration {

    @Bean
    @ConditionalOnMissingBean(AppRepository.class)
    public AppRepository appRepository(){
        return new FileAppRepositoryImpl();
    }
}
