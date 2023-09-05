package dev.notypie.application;

import dev.notypie.jwt.dto.JwtDto;
import org.springframework.http.ResponseCookie;

public interface RefreshTokenService {
    void updateRefreshToken(Long id, String refreshToken);
    boolean isDuplicateRefreshToken(Long userId);
    JwtDto refreshJwtToken(String accessToken, String refreshToken);
    ResponseCookie logoutToken(String accessToken);
    ResponseCookie createRefreshToken(String refreshToken);
}
