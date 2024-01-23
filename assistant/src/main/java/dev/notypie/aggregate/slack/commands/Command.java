package dev.notypie.aggregate.slack.commands;

import dev.notypie.aggregate.slack.dto.SlackEventContents;

public interface Command {

    String toStringCommand();
    Command getCommand();
    SlackEventContents generateEventContents();
}
