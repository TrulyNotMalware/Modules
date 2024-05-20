package dev.notypie.infrastructure.impl.command.slack.events;

import dev.notypie.infrastructure.impl.command.slack.contexts.SlackContext;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SlackCommandExecuteEvent {
    private final String transactionId;
    private final String appId;
    private final SlackEvent<SlackContext> event;
}
