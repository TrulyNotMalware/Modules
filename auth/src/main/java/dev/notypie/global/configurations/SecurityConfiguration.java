package dev.notypie.global.configurations;

import dev.notypie.application.LoginAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${authentication.login.requestUrl}")
    private String loginRequestUrl;

    @Value("${authentication.logout.requestUrl}")
    private String logoutRequestUrl;
    private final Environment environment;

    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring()//H2-console enable issue.
                .requestMatchers(new AntPathRequestMatcher("/api/auth/**"))
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**"))
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
    }

    @Bean
    @Profile("jwt")
    public SecurityFilterChain filterChain(
            HttpSecurity httpSecurity,
            LoginAuthenticationFilter filter,
            OAuth2UserService<OAuth2UserRequest, OAuth2User> userService,
            HandlerMappingIntrospector introspector
    ) throws Exception {
        //Jwt Stateless
//        httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer -> {
//            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
//        });
//        OAuth2LoginAuthenticationFilter
        httpSecurity.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
//        HttpSessionOAuth2AuthorizationRequestRepository
        httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers(new MvcRequestMatcher(introspector,"/**")).permitAll().anyRequest().authenticated());
//        ClientAuthenticationMethod.CLIENT_SECRET_POST
        httpSecurity.formLogin(form -> form.loginPage(this.loginRequestUrl).defaultSuccessUrl("/"));
        httpSecurity.logout(config -> config.logoutRequestMatcher(new AntPathRequestMatcher(this.logoutRequestUrl))
                .deleteCookies("refresh-token")//Remove refresh Token
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true));

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        //FIXME is the best way?
        //OAuth Client enabled.
        if (Arrays.asList(this.environment.getActiveProfiles()).contains("oauth-client")){
            log.info("Oauth client enabled.");
            httpSecurity.oauth2Login(config ->
                config.userInfoEndpoint(endPointConfig ->
                        endPointConfig.userService(userService)));
        }

        return httpSecurity.build();
    }

//    @Bean
//    @Profile("oauth")
//    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties properties){
//        List<ClientRegistration> registrations = properties
//                .getRegistration().keySet().stream()
//                .map(client -> getRegistration(properties, client))
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//    }
}
