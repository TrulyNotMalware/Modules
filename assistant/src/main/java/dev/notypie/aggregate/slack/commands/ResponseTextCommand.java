package dev.notypie.aggregate.slack.commands;

import dev.notypie.constants.Constants;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseTextCommand implements Command{

    private final String command;
    private final String body;

    @Builder
    ResponseTextCommand(String body){
        this.command = Constants.MENTION_COMMAND_RETURN;
        this.body = body;
    }

    @Override
    public String toStringCommand() {
        return this.command;
    }
}
