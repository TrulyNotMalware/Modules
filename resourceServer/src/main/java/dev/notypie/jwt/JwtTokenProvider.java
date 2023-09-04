package dev.notypie.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component

@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.token.accessTokenExpiredTime}")
    private Long ACCESS_TOKEN_EXPIRED;

    @Value("${jwt.token.refreshTokenExpiredTime}")
    private Long REFRESH_TOKEN_EXPIRED;

    private final RSAPrivateKey key;

    /**
     * Create JWT AccessToken Signed with RSA256
     * @param userId user identifier
     * @param roles userRoles
     * @return JwtAccessToken
     */
    public String createJwtAccessToken(String userId, List<String> roles){
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roles);

        Map<String, Object> headers = new ConcurrentHashMap<>();
        headers.put("alg", "RS256");
        headers.put("typ","JWT");
        return Jwts.builder()
                .setHeader(headers)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRED))
                .setIssuedAt(new Date())
                .signWith(this.key, SignatureAlgorithm.RS256)
                .setIssuer("notypie_dev")
                .compact();
    }

    public String createJwtRefreshToken(){
        Claims claims = Jwts.claims();
        claims.put("value", UUID.randomUUID());

        Map<String, Object> headers = new ConcurrentHashMap<>();
        headers.put("alg", "RS256");
        headers.put("typ","JWT");
        return Jwts.builder()
                .setHeader(headers)
                .addClaims(claims)
                .setExpiration(
                        new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRED)
                )
                .setIssuedAt(new Date())
                .signWith(this.key, SignatureAlgorithm.RS256)
                .compact();
    }



}
