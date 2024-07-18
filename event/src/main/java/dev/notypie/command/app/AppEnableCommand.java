package dev.notypie.command.app;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppEnableCommand {
    private String appId;
}
