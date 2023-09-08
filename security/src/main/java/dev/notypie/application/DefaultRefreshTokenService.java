package dev.notypie.application;

import dev.notypie.dao.UsersRepository;
import dev.notypie.domain.Users;
import dev.notypie.jwt.JwtTokenProvider;
import dev.notypie.jwt.dto.JwtDto;
import dev.notypie.jwt.utils.CookieProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Profile("jwt")
@RequiredArgsConstructor
public class DefaultRefreshTokenService implements RefreshTokenService{
    private final UsersRepository repository;
    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;
    private final CookieProvider cookieProvider;

    @Override
    public void updateRefreshToken(Long id, String refreshToken) {
        this.repository.updateRefreshToken(id, refreshToken);
    }

    @Override
    public boolean isDuplicateRefreshToken(Long id) {
        return this.repository.findRefreshTokenById(id) != null;
    }

    @Override
    public JwtDto refreshJwtToken(String accessToken, String refreshToken) {
        Long id = Long.valueOf(this.tokenProvider.getClaimsFromJwtToken(accessToken).getSubject());
        Users user = this.repository.findByIdWithException(id);
        String findRefreshToken = user.getRefreshToken();
        // 검증 로직 3가지, 1. AccessToken 이 Expired 됐는지,
        if(!this.tokenProvider.isExpiredToken(accessToken) ||
            !this.tokenProvider.validateJwtToken(refreshToken) ||//2. RefreshToken is valid?
            !this.tokenProvider.equalRefreshTokenId(findRefreshToken, refreshToken)){ // 3. RefreshToken is not changed
            //flush token.
            this.repository.updateRefreshToken(id, null);
            throw new RuntimeException("Refresh Failed.");
        }
        Authentication authentication = getAuthentication(user.getUserId());
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String newAccessToken = this.tokenProvider.createJwtAccessToken(String.valueOf(id), roles);
        Date expiredTime = this.tokenProvider. getClaimsFromJwtToken(newAccessToken).getExpiration();
        return JwtDto.builder().refreshToken(refreshToken).accessToken(accessToken).accessTokenExpiredDate(expiredTime)
                .build();
    }

    @Override
    public JwtDto generateNewTokens(Long id, List<String> roles) {
        String accessToken = this.tokenProvider.createJwtAccessToken(String.valueOf(id), roles);
        String refreshToken = this.tokenProvider.createJwtRefreshToken();
        Date expiredTime = this.tokenProvider.getClaimsFromJwtToken(accessToken).getExpiration();
        return JwtDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiredDate(expiredTime)
                .build();
    }

    @Override
    public ResponseCookie logoutToken(String accessToken) {
        if(!this.tokenProvider.validateJwtToken(accessToken)) throw new RuntimeException("Access token is not valid.");
        Long id = Long.valueOf(this.tokenProvider.getClaimsFromJwtToken(accessToken).getSubject());
        this.repository.updateRefreshToken(id, null);
        return this.cookieProvider.removeRefreshTokenCookie();
    }

    @Override
    public ResponseCookie createRefreshToken(String refreshToken) {
        return this.cookieProvider.createRefreshTokenCookie(refreshToken);
    }

    private Authentication getAuthentication(String userId){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
