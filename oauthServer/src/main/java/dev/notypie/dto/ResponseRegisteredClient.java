package dev.notypie.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ResponseRegisteredClient {
    private String clientId;
    private String clientSecret;
    private Instant clientSecretExpiresAt;
    private String clientName;
}
