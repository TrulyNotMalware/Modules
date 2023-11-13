package dev.notypie.jwt.utils;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseCookie;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;

@Tag("common")
@ExtendWith(MockitoExtension.class)
public class CookieProviderTest {

    private final CookieProvider cookieProvider = new CookieProvider();
    private final String refreshToken = "ThisIsTestTokenValue";
    private final String refreshTokenExpiredTime = "2000000";

    @BeforeEach
    void setUp(){
        //Reflection value
        ReflectionTestUtils.setField(cookieProvider,"refreshTokenExpiredTime",refreshTokenExpiredTime);
    }


    @Test
    @DisplayName("[mod.common] CookieProvider create responseCookie")
    void successfullyCreateResponseCookie(){
        //given
        //when
        ResponseCookie responseCookie = this.cookieProvider.createRefreshTokenCookie(this.refreshToken);
        //then
        Assertions.assertTrue(responseCookie.isHttpOnly());
        Assertions.assertTrue(responseCookie.isSecure());
        Assertions.assertEquals(responseCookie.getPath(),"/");
        Assertions.assertEquals(responseCookie.getName(), "refresh-token");
        Assertions.assertEquals(responseCookie.getValue(), this.refreshToken);
        Assertions.assertEquals(responseCookie.getMaxAge(), Duration.ofSeconds(Long.parseLong(this.refreshTokenExpiredTime)));
    }

    @Test
    @DisplayName("[mod.common] CookieProvider remove responseCookie")
    void successfullyRemoveRefreshTokenFromCookie(){
        //given
        //when
        ResponseCookie responseCookie = this.cookieProvider.removeRefreshTokenCookie();
        //then
        Assertions.assertFalse(responseCookie.isHttpOnly());
        Assertions.assertFalse(responseCookie.isSecure());
        Assertions.assertEquals(responseCookie.getPath(),"/");
        Assertions.assertEquals(responseCookie.getName(), "refresh-token");
        Assertions.assertEquals(responseCookie.getValue(), "");
        Assertions.assertEquals(responseCookie.getMaxAge(), Duration.ofSeconds(0));
    }
}
