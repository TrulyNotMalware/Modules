package dev.notypie.application;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

public enum CustomOAuth2Provider {
    MYSERVICE {
        public ClientRegistration.Builder getBuilder(String registrationId){
            ClientRegistration.Builder builder = getBuilder(
                    registrationId, ClientAuthenticationMethod.CLIENT_SECRET_BASIC, DEFAULT_LOGIN_REDIRECT_URL);
            builder.scope("message.read, message.write");
            builder.authorizationUri("http://localhost:8080/oauth2/authorize");
            builder.tokenUri("http://localhost:8080/oauth2/token");
            builder.userInfoUri("http://localhost:8080/v1/users/me");
            builder.userNameAttributeName("userId");
            builder.clientName("myService");
            return builder;
        }
    };


    private static final String DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}";

    protected final ClientRegistration.Builder getBuilder(
            String registrationId, ClientAuthenticationMethod method, String redirectUri) {
        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
        builder.clientAuthenticationMethod(method);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.redirectUri(redirectUri);
        return builder;
    }

    public abstract ClientRegistration.Builder getBuilder(String registrationId);
}
