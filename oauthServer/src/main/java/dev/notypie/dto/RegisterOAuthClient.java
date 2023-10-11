package dev.notypie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;


@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterOAuthClient {

    @JsonProperty("clientName")
    @NotBlank
    private String clientName;

    @JsonProperty("redirectUris")
    @NotBlank
    private String redirectUris;

    @JsonProperty("postLogoutRedirectUris")
    private String postLogoutRedirectUris;

    @JsonProperty("scopes")
    @NotBlank
    private Set<String> scopes;

    @Builder
    RegisterOAuthClient(String clientName, String redirectUris, String postLogoutRedirectUris, Set<String> scopes){
        this.clientName = clientName;
        this.redirectUris = redirectUris;
        this.postLogoutRedirectUris = postLogoutRedirectUris;
        this.scopes = scopes;
    }
}
