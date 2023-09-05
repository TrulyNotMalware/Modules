package dev.notypie.configurations;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring().requestMatchers(
                "/swagger-ui/**",
//                "/api/auth",
                "/h2-console/**"
        );
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings(){
        return AuthorizationServerSettings.builder().tokenEndpoint("/login/oauth2/token").build();
    }
    @Bean
    @Primary
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                           RegisteredClientRepository registeredClientRepository,
                                           OAuth2AuthorizationService authorizationService,
                                           JwtEncoder jwtEncoder,
                                           AuthorizationServerSettings settings) throws Exception {
        //JWT is Stateless
        httpSecurity.sessionManagement(
                httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.logout(config -> config.logoutRequestMatcher(new AntPathRequestMatcher("/api/user/logout"))
                .deleteCookies()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
        );
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        authorizationServerConfigurer
//                .authorizationEndpoint(it -> {it.authorizationRequestConverter()
//                it.authenticationProvider()})
                .registeredClientRepository(registeredClientRepository)
                .authorizationService(authorizationService)
                .tokenGenerator(new JwtGenerator(jwtEncoder))
                .authorizationServerSettings(settings);
        httpSecurity.apply(authorizationServerConfigurer);

        //csrf disable.
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        return httpSecurity.build();
    }
}
