package dev.notypie.infrastructure.impl.command.slack.commands;

import dev.notypie.infrastructure.impl.command.slack.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.events.SlackEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Builder
@RequiredArgsConstructor
public class CreateSlackCommand {
    @TargetAggregateIdentifier
    private final String commandId;
    private final String appId;
    private final SlackEvent<SlackContext> event;
}
