package dev.notypie.infrastructure.application;

import dev.notypie.infrastructure.impl.command.slack.commands.SlackCommand;
import dev.notypie.infrastructure.impl.command.slack.dto.contexts.Contexts;
import dev.notypie.infrastructure.impl.command.slack.event.SlackEvent;

public interface SlackCommandHandler {

    SlackCommand handleRequest(SlackEvent<? extends Contexts> event);
}
