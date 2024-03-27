package dev.notypie.event.app;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppRegisteredEvent {
    private String appId;
    private String appName;
}
