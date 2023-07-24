package dev.notypie.configurations;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
public class SecurityConfiguration {

    @Bean
    public AuthorizationServerSettings authorizationServerSettings(){
        return AuthorizationServerSettings.builder().tokenEndpoint("/v1/oauth2/token").build();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                           RegisteredClientRepository registeredClientRepository,
                                           OAuth2AuthorizationService authorizationService,
                                           JwtEncoder jwtEncoder,
                                           AuthorizationServerSettings settings) throws Exception {
        httpSecurity.apply(
        new OAuth2AuthorizationServerConfigurer()
                .registeredClientRepository(registeredClientRepository)
                .authorizationService(authorizationService)
                .tokenGenerator(new JwtGenerator(jwtEncoder))
                .authorizationServerSettings(settings));
        //csrf disable.
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        return httpSecurity.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource){
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }

    /**
     * RSA Key generate. In production, save in another key store.
     * @return JWKSource
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource(){
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey key = new RSAKey.Builder(publicKey)
                .privateKey(privateKey).keyID(UUID.randomUUID().toString())
                .build();
        return new ImmutableJWKSet<>(new JWKSet(key));
    }

    private KeyPair generateRsaKey(){
        try{
            KeyPairGenerator keys = KeyPairGenerator.getInstance("RSA");
            keys.initialize(2048);
            return keys.generateKeyPair();
        }catch(Exception e){
            throw new IllegalStateException(e);
        }
    }
}
