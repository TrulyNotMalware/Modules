package dev.notypie.jwt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = {"userId"})
@Builder
public class LoginRequestDto {

    @JsonProperty("userId")
    @NotBlank
    private String userId;

    @JsonProperty("password")
    @NotBlank
    private String password;
}
