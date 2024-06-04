package dev.notypie.infrastructure.impl.app.events;

import dev.notypie.infrastructure.impl.command.slack.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.events.SlackEvent;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AppNotRegisteredEvent {

    private final String appId;
    private final String message;
    private final SlackContext context;

}