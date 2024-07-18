package dev.notypie.command.app;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppAuthorizeCommand {
    private String creatorId;
    private String appId;
}
