package dev.notypie.jwt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class JwtDto {
    @JsonProperty("access_token")
    @NotBlank
    private String accessToken;

    @JsonProperty("refresh_token")
    @NotBlank
    private String refreshToken;
}
