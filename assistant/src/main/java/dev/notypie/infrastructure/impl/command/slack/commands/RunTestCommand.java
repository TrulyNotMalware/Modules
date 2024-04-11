package dev.notypie.infrastructure.impl.command.slack.commands;

import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventContents;
import lombok.Builder;
import lombok.Getter;

@Getter
@Deprecated(forRemoval = true)
public class RunTestCommand implements SlackCommand { // Run test code

    private final SlackCommand slackCommand;

    @Builder
    public RunTestCommand(String appName){
        //FIXME NOT IMPLEMENTED YET
//        this.command = "./gradlew clean :"+appName+":test";
        this.slackCommand = new ReturnCommand();
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
