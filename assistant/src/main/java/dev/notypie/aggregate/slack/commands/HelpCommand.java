package dev.notypie.aggregate.slack.commands;

import dev.notypie.aggregate.slack.dto.SlackEventContents;
import dev.notypie.constants.Constants;
import lombok.Getter;

@Getter
public class HelpCommand implements Command{

    private Command command;
    private String text;

    public HelpCommand(){
        this.text = """
        help : show command list
        noty : notice alarm
        """;
        this.command = ResponseTextCommand.builder()
                .body(this.text).build();
    }

    @Override
    public String toStringCommand() {
        return this.command.toStringCommand();
    }

    @Override
    public SlackEventContents generateEventContents() {
        //FIXME NOT IMPLEMENTED YET
        return null;
    }
}
