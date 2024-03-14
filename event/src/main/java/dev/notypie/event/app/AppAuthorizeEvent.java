package dev.notypie.event.app;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AppAuthorizeEvent{
    private String transactionId;
    private String appId;
}