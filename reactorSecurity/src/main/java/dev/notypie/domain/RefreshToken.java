package dev.notypie.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.annotation.Nullable;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

class RefreshToken {

    @JsonProperty("refreshToken")
    @Column("refreshToken")//490
    @Nullable
    private String refreshToken;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column("refresh_authenticated_at")
    private LocalDateTime refreshAuthenticatedAt;

    void update(String refreshToken){
        this.refreshToken = refreshToken;
        this.refreshAuthenticatedAt = LocalDateTime.now();
    }

    String getRefreshToken(){ return this.refreshToken; }
}