package dev.notypie.configurations;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

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
                "/h2-console/**",
                "/error"
        );
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings(){
        return AuthorizationServerSettings.builder().build();
    }
    @Bean
    @Profile("jpa-oauth-server")
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                           RegisteredClientRepository registeredClientRepository,
                                           OAuth2AuthorizationService authorizationService,
                                           OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService,
                                           JwtEncoder jwtEncoder,
                                           AuthorizationServerSettings settings) throws Exception {

//        filter.setSessionAuthenticationStrategy(new SessionFixationProtectionStrategy());
//        httpSecurity.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();
        RequestMatcher endpointsMatcher = authorizationServerConfigurer
                .getEndpointsMatcher();
        httpSecurity
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authorizationServerConfigurer);

        //2023.10.11 No longer need to add the Generator this way.
        //2023.09.20 JPA Duration Serialize & Deserialize issue.
        //Provider's
//        OAuth2AuthorizationCodeRequestAuthenticationProvider provider =
//                new OAuth2AuthorizationCodeRequestAuthenticationProvider(
//                        registeredClientRepository, authorizationService, oAuth2AuthorizationConsentService);
//        OAuth2AuthorizationConsentAuthenticationProvider consentProvider =
//                new OAuth2AuthorizationConsentAuthenticationProvider(
//                        registeredClientRepository, authorizationService, oAuth2AuthorizationConsentService);
//        provider.setAuthorizationCodeGenerator(new CustomOAuthAuthorizationCodeGenerator());
//        consentProvider.setAuthorizationCodeGenerator(new CustomOAuthAuthorizationCodeGenerator());
//        httpSecurity.authenticationProvider(provider);
//        httpSecurity.authenticationProvider(consentProvider);

        //2023.10.5 JwtGenerator Deserialize issue
//        CustomJwtGenerator generator = new CustomJwtGenerator(jwtEncoder);

        httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults())
                .registeredClientRepository(registeredClientRepository)
//                .clientAuthentication(clientConfigurer -> clientConfigurer.authenticationProvider(provider))
                .authorizationService(authorizationService)
                .authorizationConsentService(oAuth2AuthorizationConsentService)
                .tokenGenerator(new JwtGenerator(jwtEncoder))
                .authorizationServerSettings(settings);
        httpSecurity.formLogin(Customizer.withDefaults());

        //Redirect when acc exception.
//        httpSecurity.exceptionHandling((exceptions) ->
//                exceptions.defaultAuthenticationEntryPointFor(
//                        new LoginUrlAuthenticationEntryPoint("/login"),
//                        new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
//                )
//        );
        //csrf disable.
//        httpSecurity.csrf(AbstractHttpConfigurer::disable);
//        httpSecurity.authorizeHttpRequests(auth ->
//                auth
//                        .anyRequest().authenticated());
       return httpSecurity.build();
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> contextJWKSource){
        return new NimbusJwtEncoder(contextJWKSource);
    }

    @Bean
    public JWKSource<SecurityContext> securityContextJWKSource(RSAPrivateKey privateKey, RSAPublicKey publicKey){
        RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(
//            AuthenticationManager authenticationManager
//    ){
//        UsernamePasswordAuthenticationFilter filter = new AuthenticationFilter(authenticationManager);
//        filter.setAuthenticationManager(authenticationManager);
//        SecurityContextRepository contextRepository = new HttpSessionSecurityContextRepository();
//        filter.setSecurityContextRepository(contextRepository);
//        return filter;
//    }
}
