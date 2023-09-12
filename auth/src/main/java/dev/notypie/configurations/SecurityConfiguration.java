package dev.notypie.configurations;

import dev.notypie.application.LoginAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${authentication.login.requestUrl}")
    private String loginRequestUrl;

    @Value("${authentication.logout.requestUrl}")
    private String logoutRequestUrl;

    @Bean
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring().requestMatchers(
                "/swagger-ui/**",
                "/api/auth/**",
                "h2-console/**" //Localhost h2-console.
        );
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity httpSecurity,
            LoginAuthenticationFilter filter
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
        return httpSecurity.build();
    }
}
