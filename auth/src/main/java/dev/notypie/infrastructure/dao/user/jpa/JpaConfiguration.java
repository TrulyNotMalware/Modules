package dev.notypie.infrastructure.dao.user.jpa;

import dev.notypie.domain.user.entity.User;
import dev.notypie.domain.user.repository.UserDomainRepository;
import dev.notypie.dao.UsersRepository;
import dev.notypie.domain.Converter;
import dev.notypie.domain.Users;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "auth.app.persistent", name = "type", havingValue = "jpa")
public class JpaConfiguration {

    @Bean
    public Converter<User, Users> converter(){
        return new JpaDomainConverter();
    }

    @Bean
    public UserDomainRepository userDomainRepository(
            UsersRepository usersRepository,
            Converter<User, Users> converter
    ){
        return new JpaUserDomainRepositoryImpl(usersRepository, converter);
    }
}
