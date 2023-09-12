package dev.notypie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(of = {"userId"})
public class LoginRequestDto {

    @JsonProperty("userId")
    @NotBlank
    private String userId;

    @JsonProperty("password")
    @NotBlank
    private String password;
}
