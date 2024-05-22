package dev.notypie.infrastructure.impl.command.slack.events;

import dev.notypie.infrastructure.impl.command.slack.contexts.SlackContext;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CreateSlackCommandEvent {
    private final String commandId;
    private final String appId;
    private final SlackEvent<SlackContext> event;
}
