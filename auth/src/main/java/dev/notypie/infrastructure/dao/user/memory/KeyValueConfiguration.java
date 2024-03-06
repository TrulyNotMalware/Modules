package dev.notypie.infrastructure.dao.user.memory;

import dev.notypie.aggregate.user.repository.UserDomainRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "auth.app.persistent", name = "type", havingValue = "memory", matchIfMissing = true)
public class KeyValueConfiguration {

    @Bean
    public UserDomainRepository userDomainRepository(){
        return new KeyValueUserDomainRepositoryImpl();
    }
}
