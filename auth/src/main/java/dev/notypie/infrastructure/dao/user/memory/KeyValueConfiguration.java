package dev.notypie.infrastructure.dao.user.memory;

import dev.notypie.domain.user.repository.UserDomainRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ConditionalOnProperty(prefix = "auth.app.persistent", name = "type", havingValue = "memory", matchIfMissing = true)
public class KeyValueConfiguration {

    @Bean
    public UserDomainRepository userDomainRepository(
            PasswordEncoder passwordEncoder
    ){
        return new KeyValueUserDomainRepositoryImpl(passwordEncoder);
    }
}
