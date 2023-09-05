package dev.notypie.jwt.utils;

import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Slf4j
@Profile({"jwt","oauth"})
@Component
public class CookieProvider {

    @Value("${jwt.token.refreshTokenExpiredTime}")
    private String refreshTokenExpiredTime;

    public ResponseCookie createRefreshTokenCookie(String refreshToken){
        return ResponseCookie.from("refresh-token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Long.parseLong(refreshTokenExpiredTime)).build();
    }

    public ResponseCookie removeRefreshTokenCookie() {
        return ResponseCookie.from("refresh-token", null)
                .maxAge(0)
                .path("/")
                .build();
    }

    public static Cookie of(ResponseCookie responseCookie) {
        Cookie cookie = new Cookie(responseCookie.getName(), responseCookie.getValue());
        cookie.setPath(responseCookie.getPath());
        cookie.setSecure(responseCookie.isSecure());
        cookie.setHttpOnly(responseCookie.isHttpOnly());
        cookie.setMaxAge((int) responseCookie.getMaxAge().getSeconds());
        return cookie;
    }
}