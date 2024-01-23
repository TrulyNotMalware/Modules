package dev.notypie.aggregate.slack.commands;

import dev.notypie.aggregate.slack.dto.SlackEventContents;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RunTestCommand implements Command{ // Run test code

    private final Command command;

    @Builder
    public RunTestCommand(String appName){
        //FIXME NOT IMPLEMENTED YET
//        this.command = "./gradlew clean :"+appName+":test";
        this.command = new ReturnCommand();
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
