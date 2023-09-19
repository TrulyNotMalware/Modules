package dev.notypie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

}
