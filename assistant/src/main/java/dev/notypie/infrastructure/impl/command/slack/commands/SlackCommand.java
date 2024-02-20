package dev.notypie.infrastructure.impl.command.slack.commands;

import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventContents;

public interface SlackCommand {

    String toStringCommand();
    SlackCommand getSlackCommand();
    SlackEventContents generateEventContents();
}
