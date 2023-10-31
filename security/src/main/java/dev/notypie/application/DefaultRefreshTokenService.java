package dev.notypie.application;

import dev.notypie.dao.UsersRepository;
import dev.notypie.domain.Users;
import dev.notypie.exceptions.UserDomainException;
import dev.notypie.exceptions.UserErrorCodeImpl;
import dev.notypie.global.error.ArgumentError;
import dev.notypie.jwt.utils.JwtTokenProvider;
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

import java.util.ArrayList;
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
        // 10.31 show more detail exceptions.
        List<ArgumentError> errors = new ArrayList<>();
        if(!this.tokenProvider.isExpiredToken(accessToken))
            errors.add(new ArgumentError("access token",accessToken,"Access Token is not expired yet. This will be reported"));
        if(!this.tokenProvider.validateJwtToken(refreshToken))
            errors.add(new ArgumentError("refresh token",refreshToken,"Refresh Token is not valid. This will be reported"));
        if(!this.tokenProvider.equalRefreshTokenId(findRefreshToken, refreshToken))
            errors.add(new ArgumentError("Refresh Token","NO_DETAIL","Refresh Token is not valid. This will be reported"));
        if(!errors.isEmpty()){//Remove refreshToken.
            this.repository.updateRefreshToken(id, null);
            throw UserDomainException.builder()
                    .errorCode(UserErrorCodeImpl.TOKEN_REISSUE_FAILED).argumentErrors(errors).build();
        }

        Authentication authentication = getAuthentication(user.getUserId());
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String newAccessToken = this.tokenProvider.createJwtAccessToken(String.valueOf(id), roles);
        Date expiredTime = this.tokenProvider. getClaimsFromJwtToken(newAccessToken).getExpiration();
        return JwtDto.builder().refreshToken(refreshToken).accessToken(newAccessToken).accessTokenExpiredDate(expiredTime)
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
