package dev.notypie.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.settings.ConfigurationSettingNames;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;

@Slf4j
public class CustomOAuthAuthorizationCodeGenerator implements OAuth2TokenGenerator<OAuth2AuthorizationCode> {
    private final StringKeyGenerator authorizationCodeGenerator =
            new Base64StringKeyGenerator(Base64.getUrlEncoder().withoutPadding(), 96);

    @Nullable
    @Override
    public OAuth2AuthorizationCode generate(OAuth2TokenContext context) {
        if (context.getTokenType() == null ||
                !OAuth2ParameterNames.CODE.equals(context.getTokenType().getValue())) {
            return null;
        }
        Instant issuedAt = Instant.now();
        try {
            Instant expiresAt = issuedAt.plus(context.getRegisteredClient().getTokenSettings().getAuthorizationCodeTimeToLive());
            return new OAuth2AuthorizationCode(this.authorizationCodeGenerator.generateKey(), issuedAt, expiresAt);
        } catch (ClassCastException e){
            //Cannot type cast
            Map<String, Object> settings = context.getRegisteredClient().getTokenSettings().getSettings();
            // In my case (Oracle 19c) It's double value with seconds.
            long authorizationCodeTimeToLive = Math.round((double) settings.get(ConfigurationSettingNames.Token.AUTHORIZATION_CODE_TIME_TO_LIVE));
            Duration authorizationCodeDuration = Duration.ofSeconds(authorizationCodeTimeToLive);
            Instant expiresAt = issuedAt.plus(authorizationCodeDuration);
            return new OAuth2AuthorizationCode(this.authorizationCodeGenerator.generateKey(), issuedAt, expiresAt);
        }
    }
}
