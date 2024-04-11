package dev.notypie.infrastructure.impl.command.slack.commands;

import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventContents;
import lombok.Getter;

@Getter
@Deprecated(forRemoval = true)
public class HelpCommand implements SlackCommand {

    private SlackCommand slackCommand;
    private String text;

    public HelpCommand(){
        this.text = """
        help : show slackCommand list
        noty : notice alarm
        """;
        this.slackCommand = ResponseTextCommand.builder()
                .body(this.text).build();
    }

    @Override
    public String toStringCommand() {
        return this.slackCommand.toStringCommand();
    }

    @Override
    public SlackEventContents generateEventContents() {
        //FIXME NOT IMPLEMENTED YET
        return null;
    }
}
