package dev.notypie.configurations;

import dev.notypie.application.LoginAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

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
        return (web) -> web.ignoring().requestMatchers(
                "/swagger-ui/**",
                "/api/auth/**",
                "h2-console/**" //Localhost h2-console.
        );
    }

    @Bean
    @Profile("jwt")
    public SecurityFilterChain filterChain(
            HttpSecurity httpSecurity,
            LoginAuthenticationFilter filter,
            OAuth2UserService<OAuth2UserRequest, OAuth2User> userService
    ) throws Exception {
        //Jwt Stateless
        httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        httpSecurity.addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.authorizeHttpRequests(auth -> auth.requestMatchers("/**").permitAll().anyRequest().authenticated());

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
//    public SecurityFilterChain filterChain(
//            HttpSecurity httpSecurity,
//            OAuth2UserService<OAuth2UserRequest, OAuth2User> userService
//    ) throws Exception {
//        httpSecurity.oauth2Login(config ->
//                config.userInfoEndpoint(endPointConfig ->
//                        endPointConfig.userService(userService)));
//
//        return httpSecurity.build();
//    }
}
