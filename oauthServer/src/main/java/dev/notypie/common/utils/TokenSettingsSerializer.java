package dev.notypie.common.utils;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.ConfigurationSettingNames;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Getter
public final class TokenSettingsSerializer {
    private final TokenSettings tokenSettings;
    public TokenSettingsSerializer(TokenSettings tokenSettings){
        this.tokenSettings = this.buildTokenSettings(tokenSettings.getSettings());
    }

    public TokenSettingsSerializer(Map<String, Object> settings){
        this.tokenSettings = this.buildTokenSettings(settings);
    }

    /**
     * Fixed an issue that did not serialize correctly when reading values from a database.
     * 1. {@link java.lang.Double Double} type to {@link java.time.Duration Duration}
     * 2. {@link java.util.Map Map} type to {@link org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat OAuth2TokenFormat} instance.
     * @param settings the {@link java.util.Map Map} object from database.
     * @return {@link org.springframework.security.oauth2.server.authorization.settings.TokenSettings TokenSettings} object.
     */
    private TokenSettings buildTokenSettings(Map<String, Object> settings){
        return TokenSettings.builder()
                //Convert Duration type.
                .authorizationCodeTimeToLive(
                        this.durationConverter(getSetting(ConfigurationSettingNames.Token.AUTHORIZATION_CODE_TIME_TO_LIVE, settings))
                )
                .accessTokenTimeToLive(
                        this.durationConverter(getSetting(ConfigurationSettingNames.Token.ACCESS_TOKEN_TIME_TO_LIVE, settings))
                )
                .deviceCodeTimeToLive(
                        this.durationConverter(getSetting(ConfigurationSettingNames.Token.DEVICE_CODE_TIME_TO_LIVE, settings))
                )
                .refreshTokenTimeToLive(
                        this.durationConverter(getSetting(ConfigurationSettingNames.Token.REFRESH_TOKEN_TIME_TO_LIVE,settings))
                )
                //Others
                .reuseRefreshTokens(
                        Boolean.TRUE.equals(getSetting(ConfigurationSettingNames.Token.REUSE_REFRESH_TOKENS, settings))
                )
                .idTokenSignatureAlgorithm(
                        SignatureAlgorithm.from(getSetting(ConfigurationSettingNames.Token.ID_TOKEN_SIGNATURE_ALGORITHM, settings))
                )
                .accessTokenFormat(
                        this.tokenFormatConverter(getSetting(ConfigurationSettingNames.Token.ACCESS_TOKEN_FORMAT, settings),null)
                )
                .build();
    }

    /**
     * Convert double value to Duration type.
     * change Double value to Duration of seconds.
     * @param value
     *        The {@link java.lang.Double Double} value.
     * @return {@link java.time.Duration duration} instance.
     */
    private Duration durationConverter(Double value){
        return Duration.ofSeconds(Math.round(value));
    }

    /**
     * Cast the {@link java.util.Map Map} instance to {@link org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat OAuth2TokenFormat} object.
     * this function throws {@link java.lang.IllegalArgumentException IllegalArgumentException} when cannot type cast.
     * @param map the data object
     * @param keyName Nullable string key name. the default key name is "value", but you could also change this.
     * @see IllegalArgumentException
     * @return {@link org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat OAuth2TokenFormat} instance.
     */
    private OAuth2TokenFormat tokenFormatConverter(Map<String, Object> map, @Nullable String keyName){
        //in my case, value from database is LinkedHashMap.
        Assert.notEmpty(map, "Map object is empty.");
        keyName = Objects.requireNonNullElse(keyName, "value");
        if(OAuth2TokenFormat.SELF_CONTAINED.getValue().equals(getSetting(keyName, map))) return OAuth2TokenFormat.SELF_CONTAINED;
        else if(OAuth2TokenFormat.REFERENCE.getValue().equals(getSetting(keyName, map))) return OAuth2TokenFormat.REFERENCE;
        throw new IllegalArgumentException("Cannot convert "+getSetting(keyName, map)+"to OAuth2TokenFormat.");
    }

    /**
     * get value from Map object. this function throws IllegalArgumentException.
     * @see IllegalArgumentException
     * @param name The key name for extract from map.
     * @param settings the Map object.
     * @param <T> Return type.
     */
    private <T> T getSetting(String name, Map<String, Object> settings){
        Assert.hasText(name, "name cannot be empty");
        Assert.notEmpty(settings,"Map object is empty.");
        Assert.notNull(settings.get(name),"Value not exist.");
        return (T) settings.get(name);
        //if you require null handling,
//        if(settings.get(name) == null){
//            log.warn("Value {} is empty.", name);
//            return null;
//        }
//        else return (T) settings.get(name);
    }
}
