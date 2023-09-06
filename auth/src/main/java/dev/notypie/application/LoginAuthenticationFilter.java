package dev.notypie.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.dto.LoginRequestDto;
import dev.notypie.jwt.JwtTokenProvider;
import dev.notypie.jwt.dto.JwtDto;
import dev.notypie.jwt.utils.CookieProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Profile("jwt")
@RequiredArgsConstructor
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
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
        User user = (User) authResult.getPrincipal();
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        Long id = Long.valueOf(user.getUsername());
        //Multiple Login
        if(this.refreshTokenService.isDuplicateRefreshToken(id)){
            this.refreshTokenService.updateRefreshToken(id, null);
            throw new RuntimeException("Multiple Login detected.");
        }
        JwtDto newToken = this.refreshTokenService.generateNewTokens(id, roles);
        ResponseCookie refreshTokenCookie = this.refreshTokenService.createRefreshToken(newToken.getRefreshToken());
        Cookie cookie = CookieProvider.of(refreshTokenCookie);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.addCookie(cookie);

        Map<String, Object> tokens = Map.of(
                "id",id,
                "accessToken", newToken.getAccessToken(),
                "expiredTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(newToken.getAccessTokenExpiredDate()),
                "message", "login success"
        );
        this.objectMapper.writeValue(response.getOutputStream(), tokens);
    }
}
