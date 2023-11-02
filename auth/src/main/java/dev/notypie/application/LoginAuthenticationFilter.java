package dev.notypie.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.exceptions.UserDomainException;
import dev.notypie.exceptions.UserErrorCodeImpl;
import dev.notypie.jwt.dto.LoginRequestDto;
import dev.notypie.jwt.dto.JwtDto;
import dev.notypie.jwt.utils.CookieGenerator;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Profile("jwt")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final ObjectMapper objectMapper;

    @Value("${authentication.login.requestUrl}")
    private String loginRequestUrl;

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
//            throw new RuntimeException("Multiple Login detected.");
            throw new UserDomainException(UserErrorCodeImpl.AUTHENTICATION_FAILED, new ArrayList<>());
        }
        JwtDto newToken = this.refreshTokenService.generateNewTokens(id, roles);

        //Save refreshToken
        this.refreshTokenService.updateRefreshToken(id, newToken.getRefreshToken());

        ResponseCookie refreshTokenCookie = this.refreshTokenService.createRefreshToken(newToken.getRefreshToken());
        //[9.26] Module Changed to common, implementation change.
        Cookie cookie = CookieGenerator.of(refreshTokenCookie);

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

    @PostConstruct
    public void configure(){
        this.setAuthenticationManager(this.authenticationManager);
        this.setFilterProcessesUrl(this.loginRequestUrl);
        SecurityContextRepository contextRepository = new HttpSessionSecurityContextRepository();
        this.setSecurityContextRepository(contextRepository);
    }
}
