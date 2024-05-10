package dev.notypie.infrastructure.impl.command.slack.event;

import dev.notypie.infrastructure.impl.command.slack.contexts.SlackContext;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SlackCommandExecuteEvent {
    private final Long transactionId;
    private final String appId;
    private final SlackEvent<SlackContext> event;
}
