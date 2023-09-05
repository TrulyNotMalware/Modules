package dev.notypie.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.dto.LoginRequestDto;
import dev.notypie.jwt.JwtTokenProvider;
import dev.notypie.jwt.utils.CookieProvider;
import jakarta.servlet.FilterChain;
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
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@Profile("jwt")
@RequiredArgsConstructor
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieProvider cookieProvider;
    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException
    {
        try{
            LoginRequestDto requestDto = this.objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);
            return this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUserId(), requestDto.getPassword()
                    )
            );
        }catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult
    ) throws IOException {

    }
}
