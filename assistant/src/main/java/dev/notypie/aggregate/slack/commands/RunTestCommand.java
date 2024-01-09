package dev.notypie.aggregate.slack.commands;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RunTestCommand implements Command{ // Run test code

    private String command;

    @Builder
    public RunTestCommand(String appName){
        this.command = "./gradlew clean :"+appName+":test";
    }

    @Override
    public String toStringCommand() {
        return this.command;
    }
}
