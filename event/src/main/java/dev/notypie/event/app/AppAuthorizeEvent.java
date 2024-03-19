package dev.notypie.event.app;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class AppAuthorizeEvent {
    private String transactionId;
    private String appId;
    private String ownerId;
}