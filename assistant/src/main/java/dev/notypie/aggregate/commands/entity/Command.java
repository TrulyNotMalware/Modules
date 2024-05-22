package dev.notypie.aggregate.commands.entity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;


@Getter
public class Command {

    @NotNull
    private final String commandId;

    @NotBlank
    private final String appId;

    @NotBlank
    private final String commandType;

    @NotNull
    private final Long publisherId;

    @NotNull
    private final CommandContext context;


    @Builder(builderMethodName = "NewCommand")
    public Command(@NonNull String appId, @NonNull String commandType,
                   @NonNull Long publisherId, @NonNull CommandContext commandContext){
        this.commandId = this.generateIdValue();
        this.appId = appId;
        this.commandType = commandType;
        this.publisherId = publisherId;
        this.context = commandContext;
    }

    public boolean verifyCommand(){
        this.verifyCommandAuthorization();
        this.verifyCommandValidate();
        return true;
    }

    private void verifyCommandAuthorization(){
        //FIXME Saga Assignment
    }

    private void verifyCommandValidate(){
        this.context.validateCommand();
    }

    public void executeCommand(){
        if(this.verifyCommand()) this.context.executeCommand();
        else throw new RuntimeException("Command Verification failed");
    }

    private String generateIdValue(){
        return UUID.randomUUID().toString();
    }
}
