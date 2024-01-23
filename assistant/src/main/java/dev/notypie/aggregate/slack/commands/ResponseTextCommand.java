package dev.notypie.aggregate.slack.commands;

import dev.notypie.aggregate.slack.dto.SlackEventContents;
import dev.notypie.constants.Constants;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseTextCommand implements Command{

    private final Command command;
    private final String body;

    @Builder
    ResponseTextCommand(String body){
        this.command = new ReturnCommand();
        this.body = body;
    }

    @Override
    public String toStringCommand() {
        return this.command.toStringCommand();
    }

    @Override
    public SlackEventContents generateEventContents() {
        return null;
    }
}
