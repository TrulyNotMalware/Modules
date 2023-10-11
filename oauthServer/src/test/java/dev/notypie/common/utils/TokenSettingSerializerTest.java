package dev.notypie.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.domain.Client;
import dev.notypie.dto.RegisterOAuthClient;
import dev.notypie.jpa.dao.ClientRepository;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@DataJpaTest
@ActiveProfiles({"test", "jpa-oauth-server"})
@TestPropertySource(locations = "classpath:application-test.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Use Test database for Real database.
public class TokenSettingSerializerTest {

    private final ClientRepository clientRepository;
    private RegisterOAuthClient blueprint;

    @Autowired
    TokenSettingSerializerTest(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @BeforeEach
    void createClientRepository(){
        Set<String> scopes = new HashSet<>();
        scopes.add("test.read");
        scopes.add("test.write");

        this.blueprint = RegisterOAuthClient.builder()
                .clientName("testClient")
                .redirectUris("http://localhost/login/oauth2/code/test")//Only for test.
                .postLogoutRedirectUris("http://localhost/logout")
                .scopes(scopes)
                .build();
    }

    @Test
    @DisplayName("Success deserialize")
    void successfullyDeserializeTokenSettings() throws JsonProcessingException {
        //given
        Client client = Client.builder().oauthClient(this.blueprint).build();
        TokenSettings original = TokenSettings.builder().build();
        //when
        this.clientRepository.save(client);
        Client getClient = this.clientRepository.findByClientId(client.getClientId()).orElse(null);
        Assert.notNull(getClient);

        Map<String, Object> parsedMap = new ObjectMapper().readValue(getClient.getTokenSettings(), new TypeReference<>() {});
        Assert.notEmpty(parsedMap);
        TokenSettingsSerializer tokenSerializer = new TokenSettingsSerializer(parsedMap);
        TokenSettings deserializedTokenSettings = tokenSerializer.getTokenSettings();

        //then
        //1. OAuthTokenFormat
        Assert.isTrue(deserializedTokenSettings.getAccessTokenFormat().equals(original.getAccessTokenFormat()));
        //2. Duration value
        Assert.isTrue(deserializedTokenSettings.getAccessTokenTimeToLive().equals(original.getAccessTokenTimeToLive()));
        Assert.isTrue(deserializedTokenSettings.getAuthorizationCodeTimeToLive().equals(original.getAuthorizationCodeTimeToLive()));
        Assert.isTrue(deserializedTokenSettings.getDeviceCodeTimeToLive().equals(original.getDeviceCodeTimeToLive()));
        Assert.isTrue(deserializedTokenSettings.getRefreshTokenTimeToLive().equals(original.getRefreshTokenTimeToLive()));
        //3. Signature Algorithm
        Assert.isTrue(deserializedTokenSettings.getIdTokenSignatureAlgorithm().equals(original.getIdTokenSignatureAlgorithm()));
    }
}
