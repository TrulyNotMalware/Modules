package dev.notypie.aggregate.commands.entity;


import dev.notypie.global.error.exceptions.CommandErrorCodeImpl;
import dev.notypie.global.error.exceptions.CommandException;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class Command {

    private final Long commandId;
    private final String appId;
    private String commandType;
    private final Long publisherId;
    private CommandContext context;

    @Builder
    public Command(Long commandId, String appId, Long publisherId, String commandType){
        if(commandId == null) this.commandId = 0L;
        else this.commandId = commandId;
        this.appId = appId;
        this.publisherId = publisherId;
        this.commandType = commandType;
    }


    @Builder(builderMethodName = "NewCommand")
    public Command(@NotNull String appId, @NotNull String commandType,
                   @NotNull Long publisherId, @NotNull CommandContext commandContext){
        this.commandId = this.generateIdValue();
        this.appId = appId;
        this.commandType = commandType;
        this.publisherId = publisherId;
        this.context = commandContext;
    }

    private void parseAndInsertValueFromMap(){

    }

    private Long generateIdValue(){
        return 0L;
    }
}
