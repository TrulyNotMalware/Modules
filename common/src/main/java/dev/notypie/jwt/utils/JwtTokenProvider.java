package dev.notypie.jwt.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Profile("jwt")
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.token.accessTokenExpiredTime}")
    private Long ACCESS_TOKEN_EXPIRED;

    @Value("${jwt.token.refreshTokenExpiredTime}")
    private Long REFRESH_TOKEN_EXPIRED;

    private final RSAPrivateKey key;
    private final RSAPublicKey publicKey;

    /**
     * Create JWT AccessToken Signed with RSA256
     * @param id user identifier - Primary key
     * @param roles userRoles
     * @return JwtAccessToken
     */
    public String createJwtAccessToken(String id, List<String> roles){
        Claims claims = Jwts.claims().setSubject(id);
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

    public Claims getClaimsFromJwtToken(String token){
        try{
            return Jwts.parserBuilder().setSigningKey(this.publicKey).build().parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            log.error("Error in getClaims");
            return e.getClaims();
        }catch (Exception e) {
            log.error("Error in refresh");
            e.printStackTrace();
            throw e;
        }
    }

    public boolean isExpiredToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token).getBody().getExpiration();
            return false;
        } catch(ExpiredJwtException e){
            log.error("Expired JWT Tokens : {}", token);
            return true;
        }
    }

    public boolean validateJwtToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(this.publicKey).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e){
            log.error("Invalid JWT signature: {}", e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            return false;
        }
    }

    public boolean equalRefreshTokenId(String findRefreshToken, String refreshToken) {
        String tokenId = this.getClaimsFromJwtToken(findRefreshToken).get("value").toString();
        String compareToken = this.getClaimsFromJwtToken(refreshToken).get("value").toString();
        return tokenId.equals(compareToken);
    }
}