package dev.notypie.infrastructure.service.command;

import dev.notypie.infrastructure.impl.command.slack.commands.SlackCommand;
import dev.notypie.infrastructure.impl.command.slack.dto.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.event.SlackEvent;

public interface SlackCommandHandler {

    SlackCommand generateSlackCommand(SlackEvent<? extends SlackContext> event);
    SlackCommand generateSlackCommand(SlackContext context);
}
