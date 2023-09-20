package dev.notypie.configurations;

import dev.notypie.jwt.dto.LoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
@Profile("jwt")
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            log.info("attemptAuthentication");
            StringBuilder builder = new StringBuilder();
            InputStream inputStream = null;
            BufferedReader br = null;
            String line = "";
            try {
                inputStream = request.getInputStream();
                if (inputStream != null) {
                    br = new BufferedReader(new InputStreamReader(inputStream));
                    while ((line = br.readLine()) != null) {
                        builder.append(line);
                    }
                }
                String json = builder.toString();
                log.info("json : {}",json);
                String[] list = json.split("&");
                String[] userId = list[0].split("=");
                String[] password = list[1].split("=");
                LoginRequestDto requestDto = LoginRequestDto.builder()
                        .userId(userId[1])
                        .password(password[1])
                        .build();
                return this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUserId(), requestDto.getPassword()
                    )
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch ( Exception e ){
            throw new RuntimeException(e);
        }
    }
}