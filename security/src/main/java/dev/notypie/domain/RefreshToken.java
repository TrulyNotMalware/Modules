package dev.notypie.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
class RefreshToken {

    @JsonProperty("refreshToken")
    @Column(name = "refresh_token", length = 500)//490
    @Nullable
    private String refreshToken;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "refresh_authenticated_at")
    private LocalDateTime refreshAuthenticatedAt;

    void update(String refreshToken){
        this.refreshToken = refreshToken;
        this.refreshAuthenticatedAt = LocalDateTime.now();
    }

    String getRefreshToken(){ return this.refreshToken; }
}
