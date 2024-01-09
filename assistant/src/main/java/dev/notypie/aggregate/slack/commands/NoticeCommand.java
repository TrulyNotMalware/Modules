package dev.notypie.aggregate.slack.commands;

import lombok.Getter;

@Getter
public class NoticeCommand implements Command{

    private Command command;

    public NoticeCommand(String message, String user){

    }

    @Override
    public String toStringCommand() {
        return command.toStringCommand();
    }
}
