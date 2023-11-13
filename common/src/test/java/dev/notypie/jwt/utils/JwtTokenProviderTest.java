package dev.notypie.jwt.utils;

import dev.notypie.base.SpringIntegrationTest;
import dev.notypie.builders.JwtTokenBuilder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;

@Tag("common")
@ActiveProfiles("jwt")
public class JwtTokenProviderTest extends SpringIntegrationTest {

    @Autowired
    private JwtTokenProvider provider;
    @Autowired
    private RSAPrivateKey privateKey;
    @Autowired
    private RSAPublicKey publicKey;

    @Value("${jwt.token.accessTokenExpiredTime}")
    private Long ACCESS_TOKEN_EXPIRED;

    @Value("${jwt.token.refreshTokenExpiredTime}")
    private Long REFRESH_TOKEN_EXPIRED;

    private String id;
    private List<String> roles;
    private String accessToken;
    private String refreshToken;

    private RSAPrivateKey unKnownPrivateKey;
    private RSAPublicKey unKnownPublicKey;
    private JwtTokenBuilder builder;
    private JwtTokenBuilder expiredTokenBuilder;
    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        this.id = "IamTestId";
        this.roles = new ArrayList<>();
        roles.add("User");

        this.accessToken = this.provider.createJwtAccessToken(this.id, this.roles);
        this.refreshToken = this.provider.createJwtRefreshToken();
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        this.unKnownPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.unKnownPublicKey = (RSAPublicKey) keyPair.getPublic();
        this.builder = new JwtTokenBuilder(unKnownPrivateKey, unKnownPublicKey);
        this.expiredTokenBuilder = new JwtTokenBuilder(this.privateKey, this.publicKey);
    }

    @Test
    @DisplayName("[mod.common] Jwt claim parser works successfully")
    void getClaimsFromJwtToken() {
        //given
        String expiredToken = this.expiredTokenBuilder.buildRefreshToken(0L);
        //when
        Claims claims = this.provider.getClaimsFromJwtToken(this.accessToken);
        Claims refreshClaims = this.provider.getClaimsFromJwtToken(this.refreshToken);
        //then

        Assertions.assertEquals(claims.getSubject(),this.id);
        Assertions.assertEquals(claims.get("roles"), this.roles);
        Assertions.assertNotNull(refreshClaims.get("value"));
    }


    @Test
    @DisplayName("[mod.common] Check is Valid token")
    void validateJwtToken() {
        //given
        String invalidSignature = this.builder.buildRefreshToken(0L);
        String malformedJwtToken = "I AM MALFORMED TOKEN";
        String malformedJwtToken2 = "easd.easd.eazzxx";
        String expiredToken = this.expiredTokenBuilder.buildRefreshToken(0L);
        //when
        boolean invalidSign = this.provider.validateJwtToken(invalidSignature);
        boolean malformedToken = this.provider.validateJwtToken(malformedJwtToken);
        boolean malformedToken2 = this.provider.validateJwtToken(malformedJwtToken2);
        boolean jwtExpiredException = this.provider.validateJwtToken(expiredToken);
        //then
        Assertions.assertFalse(invalidSign);
        Assertions.assertFalse(malformedToken);
        Assertions.assertFalse(malformedToken2);
        Assertions.assertFalse(jwtExpiredException);
    }

    @Test
    @DisplayName("[mod.common] Check expiredToken")
    void isExpiredToken() {
        //given
        String expiredToken = this.expiredTokenBuilder.buildRefreshToken(0L);
        //when
        boolean notExpired = this.provider.isExpiredToken(this.refreshToken);
        boolean expired = this.provider.isExpiredToken(expiredToken);
        //then
        Assertions.assertFalse(notExpired);
        Assertions.assertTrue(expired);
    }

    @Test
    @DisplayName("[mod.common] Check refreshTokens has same UUID value")
    void equalRefreshTokenId() {
        //given
        String unknownSignatureToken = this.builder.buildRefreshToken(2000L);
        String expiredRefreshToken = this.expiredTokenBuilder.buildRefreshToken(0L);
        //when
        boolean isOk = this.provider.equalRefreshTokenId(this.refreshToken, this.refreshToken);
        boolean isEqualValue = this.provider.equalRefreshTokenId(expiredRefreshToken, expiredRefreshToken);//This will be OK.
        boolean isNotEqual = this.provider.equalRefreshTokenId(this.refreshToken, expiredRefreshToken);
        //then
        Assertions.assertThrows(SignatureException.class,
                () -> this.provider.equalRefreshTokenId(this.refreshToken, unknownSignatureToken));

        Assertions.assertTrue(isOk);
        Assertions.assertTrue(isEqualValue);
        Assertions.assertFalse(isNotEqual);
    }

}
