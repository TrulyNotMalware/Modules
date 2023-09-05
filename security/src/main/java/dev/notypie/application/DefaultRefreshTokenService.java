package dev.notypie.application;

import dev.notypie.jwt.dto.JwtDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultRefreshTokenService implements RefreshTokenService{

    @Override
    public void updateRefreshToken(Long id, String refreshToken) {

    }

    @Override
    public boolean isDuplicateRefreshToken(Long userId) {
        return false;
    }

    @Override
    public JwtDto refreshJwtToken(String accessToken, String refreshToken) {
        return null;
    }

    @Override
    public ResponseCookie logoutToken(String accessToken) {
        return null;
    }

    @Override
    public ResponseCookie createRefreshToken(String refreshToken) {
        return null;
    }
}
