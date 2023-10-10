package dev.notypie.configurations;

import dev.notypie.application.JpaOAuth2AuthorizationConsentService;
import dev.notypie.application.JpaOAuth2AuthorizationService;
import dev.notypie.jpa.dao.AuthorizationConsentRepository;
import dev.notypie.jpa.dao.AuthorizationRepository;
import dev.notypie.jpa.dao.ClientRepository;
import dev.notypie.jpa.dao.JpaRegisteredClientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@Configuration
@Profile("jpa-oauth-server")
public class JpaConfiguration {

    @Bean
    RegisteredClientRepository registeredClient(ClientRepository clientRepository){
        return new JpaRegisteredClientRepository(clientRepository);
    }

    @Bean
    OAuth2AuthorizationService authorizationService(AuthorizationRepository authorizationRepository,
                                                    RegisteredClientRepository registeredClientRepository){
        return new JpaOAuth2AuthorizationService(authorizationRepository, registeredClientRepository);
    }

    @Bean
    OAuth2AuthorizationConsentService authorizationConsentService(AuthorizationConsentRepository repository,
                                                                  RegisteredClientRepository registeredClientRepository){
        return new JpaOAuth2AuthorizationConsentService(repository, registeredClientRepository);
    }
}
