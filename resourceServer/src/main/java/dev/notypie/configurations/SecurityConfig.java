package dev.notypie.configurations;

import dev.notypie.authentication.AccessDeniedHandler;
import dev.notypie.authentication.EntryPoint;
import dev.notypie.authentication.SimpleJwtAuthenticationConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain jwtChain(HttpSecurity httpSecurity,
                                        SimpleJwtAuthenticationConverter jwtAuthenticationConverter,
                                        JwtDecoder jwtDecoder) throws Exception {
        // in Spring 6, remove deprecated method jwt().
        httpSecurity.oauth2ResourceServer(oauth2ResourceServerConfigurer -> {
           oauth2ResourceServerConfigurer.jwt(jwtConfigurer -> {
               jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter);
               jwtConfigurer.decoder(jwtDecoder);
           });
           oauth2ResourceServerConfigurer.accessDeniedHandler(new AccessDeniedHandler());
           oauth2ResourceServerConfigurer.authenticationEntryPoint(new EntryPoint());
        });

        return httpSecurity.build();
    }
}
