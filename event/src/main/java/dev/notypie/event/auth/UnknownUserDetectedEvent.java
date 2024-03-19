package dev.notypie.event.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UnknownUserDetectedEvent {
    private String userId;
    private String description;
}
