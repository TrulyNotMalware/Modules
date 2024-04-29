package dev.notypie.infrastructure.impl.command.slack.commands;

import dev.notypie.infrastructure.impl.command.slack.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.event.SlackEvent;
import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Builder
public class ExecuteSlackCommand {
    @TargetAggregateIdentifier
    private final String appId;
    private final SlackEvent<SlackContext> event;
}
