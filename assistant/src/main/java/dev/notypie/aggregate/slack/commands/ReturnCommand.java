package dev.notypie.aggregate.slack.commands;

import dev.notypie.aggregate.slack.dto.SlackEventContents;
import dev.notypie.global.constants.Constants;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReturnCommand implements Command{
    @Override
    public String toStringCommand() {
        return Constants.MENTION_COMMAND_RETURN;
    }

    @Override
    public Command getCommand() {
        return this;
    }

    @Override
    public SlackEventContents generateEventContents() {
        return null;
    }
}
