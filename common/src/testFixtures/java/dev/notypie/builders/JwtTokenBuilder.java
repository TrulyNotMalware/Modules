package dev.notypie.builders;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class JwtTokenBuilder {
    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;
    public void setPrivateKey(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public void setPublicKey(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public JwtTokenBuilder(RSAPrivateKey privateKey, RSAPublicKey publicKey){
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public JwtTokenBuilder() throws NoSuchAlgorithmException {
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
    }

    public String buildAccessToken(String id, List<String> roles, Long accessTokenTimeout){
        Claims claims = Jwts.claims().setSubject(id);
        claims.put("roles", roles);

        Map<String, Object> headers = new ConcurrentHashMap<>();
        headers.put("alg", "RS256");
        headers.put("typ","JWT");
        return Jwts.builder()
                .setHeader(headers)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenTimeout))
                .setIssuedAt(new Date())
                .signWith(this.privateKey, SignatureAlgorithm.RS256)
                .setIssuer("tester")
                .compact();
    }

    public String buildRefreshToken(Long refreshTokenTimeout){
        Claims claims = Jwts.claims();
        claims.put("value", UUID.randomUUID());

        Map<String, Object> headers = new ConcurrentHashMap<>();
        headers.put("alg", "RS256");
        headers.put("typ","JWT");
        return Jwts.builder()
                .setHeader(headers)
                .addClaims(claims)
                .setExpiration(
                        new Date(System.currentTimeMillis() + refreshTokenTimeout)
                )
                .setIssuedAt(new Date())
                .signWith(this.privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
}
