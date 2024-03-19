package dev.notypie.command.app;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Builder
public class AppEnableCommand {
    @TargetAggregateIdentifier
    private String appId;
}
