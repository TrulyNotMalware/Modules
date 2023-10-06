package dev.notypie.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.uuid.Generators;
import dev.notypie.dto.RegisterOAuthClient;
import dev.notypie.dto.ResponseRegisteredClient;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;


import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Slf4j
@Entity
@Table(name = "client")
@Getter
@Setter
@SequenceGenerator(
        name = "CLIENT_SQ_GENERATOR",
        sequenceName = "CLIENT_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "CLIENT_SEQ"
    )
    private Long id;
    private String clientId;
    private Instant clientIdIssuedAt;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    private String clientName;
    @Column(length = 1000)
    private String clientAuthenticationMethods;
    @Column(length = 1000)
    private String authorizationGrantTypes;
    @Column(length = 1000)
    private String redirectUris;
    @Column(length = 1000)
    private String postLogoutRedirectUris;
    @Column(length = 1000)
    private String scopes;
    @Column(length = 2000)
    private String clientSettings;
    @Column(length = 2000)
    private String tokenSettings;

    @Transient
    private String rawPassword;
    @Builder
    protected Client(RegisterOAuthClient oauthClient){
        this.clientId = this.generateClientId();
        this.clientIdIssuedAt = Instant.now();
        this.rawPassword = this.generatePassword();// Require Raw Password for client.
        this.clientSecret = new BCryptPasswordEncoder().encode(this.rawPassword);
        this.clientSecretExpiresAt = Instant.now().plus(24, ChronoUnit.HOURS);// 24Hours later.
        this.clientName = oauthClient.getClientName();
        this.clientAuthenticationMethods = ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue(); // Default.
        this.authorizationGrantTypes = AuthorizationGrantType.AUTHORIZATION_CODE.getValue();
        this.redirectUris = oauthClient.getRedirectUris();
        this.postLogoutRedirectUris = oauthClient.getPostLogoutRedirectUris();
        this.scopes = StringUtils.collectionToCommaDelimitedString(oauthClient.getScopes());
        if (this.clientSettings == null) {
            ClientSettings.Builder builder = ClientSettings.builder();
            builder.requireAuthorizationConsent(true);
//            if (isPublicClientType()) {
//                builder
//                        .requireProofKey(true)
//                        .requireAuthorizationConsent(true);
//            }
            this.clientSettings = writeMap(builder.build().getSettings());
        }
        if (this.tokenSettings == null) {
            this.tokenSettings = writeMap(TokenSettings.builder().build().getSettings());
        }
    }

    private String writeMap(Map<String, Object> data) {
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
    private boolean isPublicClientType() {
        Set<String> authenticationMethods = StringUtils.commaDelimitedListToSet(this.clientAuthenticationMethods);
        return StringUtils.commaDelimitedListToSet(this.authorizationGrantTypes).contains(AuthorizationGrantType.AUTHORIZATION_CODE.getValue()) &&
                authenticationMethods.size() == 1 &&
                authenticationMethods.contains(ClientAuthenticationMethod.NONE.getValue());
    }
    public static Client createDefaultClient(RegisterOAuthClient blueprint){
        return Client.builder().oauthClient(blueprint).build();
    }

    private String generateClientId(){
        return Generators.timeBasedGenerator().generate().toString().replaceAll("-","");
    }

    private String generatePassword(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 30;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public ResponseRegisteredClient toResponseDto(){
        return ResponseRegisteredClient.builder()
                .clientId(this.clientId)
                .clientSecret(this.rawPassword)// Client needs raw password.
                .clientSecretExpiresAt(this.clientSecretExpiresAt)
                .clientName(this.clientName)
                .build();
    }
}