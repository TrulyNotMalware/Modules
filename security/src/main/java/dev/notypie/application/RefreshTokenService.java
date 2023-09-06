package dev.notypie.application;

import dev.notypie.domain.Users;
import dev.notypie.jwt.dto.JwtDto;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseCookie;

import java.util.List;

@Profile("jwt")
public interface RefreshTokenService {
    Users updateRefreshToken(Long id, String refreshToken);
    boolean isDuplicateRefreshToken(Long id);
    JwtDto refreshJwtToken(String accessToken, String refreshToken);
    JwtDto generateNewTokens(Long id, List<String> roles);
    ResponseCookie logoutToken(String accessToken);
    ResponseCookie createRefreshToken(String refreshToken);

}
