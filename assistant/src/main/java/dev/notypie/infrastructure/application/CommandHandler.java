package dev.notypie.infrastructure.application;

import dev.notypie.aggregate.slack.commands.Command;
import dev.notypie.aggregate.slack.dto.contexts.Contexts;
import dev.notypie.aggregate.slack.event.SlackEvent;

public interface CommandHandler {

    Command handleRequest(SlackEvent<? extends Contexts> event);
}
