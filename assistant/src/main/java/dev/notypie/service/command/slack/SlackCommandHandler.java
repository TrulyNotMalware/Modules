package dev.notypie.service.command.slack;

import dev.notypie.infrastructure.impl.command.slack.commands.SlackCommand;
import dev.notypie.infrastructure.impl.command.slack.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.event.SlackEvent;

@Deprecated(forRemoval = true)
public interface SlackCommandHandler {

    SlackCommand generateSlackCommand(SlackEvent<? extends SlackContext> event);
    SlackCommand generateSlackCommand(SlackContext context);
}
