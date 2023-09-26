package dev.notypie.jwt.utils;


import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseCookie;

@Profile({"jwt","oauth"})
public class CookieGenerator {

    public static Cookie of(ResponseCookie responseCookie) {
        Cookie cookie = new Cookie(responseCookie.getName(), responseCookie.getValue());
        cookie.setPath(responseCookie.getPath());
        cookie.setSecure(responseCookie.isSecure());
        cookie.setHttpOnly(responseCookie.isHttpOnly());
        cookie.setMaxAge((int) responseCookie.getMaxAge().getSeconds());
        return cookie;
    }
}
