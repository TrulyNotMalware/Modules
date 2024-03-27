package dev.notypie.command.app;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Builder
public class AppAuthorizeCommand {
    @TargetAggregateIdentifier
    private String appId;
    private String creatorId;
}
