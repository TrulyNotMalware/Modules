package dev.notypie.infrastructure.impl.command.slack.commands;

import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventContents;
import dev.notypie.global.constants.Constants;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReturnCommand implements SlackCommand {
    @Override
    public String toStringCommand() {
        return Constants.MENTION_COMMAND_RETURN;
    }

    @Override
    public SlackCommand getSlackCommand() {
        return this;
    }


    @Override
    public SlackEventContents generateEventContents() {
        return null;
    }
}
