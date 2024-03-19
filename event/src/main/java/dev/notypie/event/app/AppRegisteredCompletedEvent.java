package dev.notypie.event.app;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AppRegisteredCompletedEvent {
    private String appId;
    private LocalDateTime authenticatedDate;
}
