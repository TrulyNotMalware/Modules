package dev.notypie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.notypie.jwt.dto.JwtDto;
import lombok.Builder;
import lombok.Getter;

import java.text.SimpleDateFormat;

@Getter
@Builder
public class TokenResponseDto {

    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("expire")
    private String expired;


    public static TokenResponseDto toTokenResponseDto(JwtDto jwt){
        return TokenResponseDto.builder()
                .accessToken(jwt.getAccessToken())
                .expired(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        .format(jwt.getAccessTokenExpiredDate()))
                .build();
    }
}
