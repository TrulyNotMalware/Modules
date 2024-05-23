package dev.notypie.infrastructure.impl.app.commands;

import dev.notypie.infrastructure.impl.command.slack.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.events.SlackEvent;
import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Getter
public class VerifySlackAppCommand {
    @TargetAggregateIdentifier
    private final String appId;
    private final String creatorId;
    private final SlackContext context;
}
