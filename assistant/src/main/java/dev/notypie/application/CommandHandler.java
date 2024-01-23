package dev.notypie.application;

import dev.notypie.aggregate.slack.commands.Command;
import dev.notypie.aggregate.slack.event.SlackEvent;

public interface CommandHandler {

    Command handleRequest(SlackEvent<?> event);
}
