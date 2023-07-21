package dev.notypie.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.UUID;

@Configuration
public class SecurityConfiguration {

    @Bean
    public RegisteredClientRepository registeredClientRepository(){
        RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientName("notypie client")
                .clientId("notypie")
//                .clientSecret("")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientSettings(ClientSettings.builder().build())
                .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofMinutes(10)).build()).build();
        return new InMemoryRegisteredClientRepository(client);
    }
//    @Bean
//    public void filterChain(HttpSecurity httpSecurity,
//                            RegisteredClientRepository registeredClientRepository,
//                            OAuth2AuthorizationService authorizationService,
//                            JwtEncoder jwtEncoder,
//                            AuthorizationServerSettings settings){
//
//    }
}
