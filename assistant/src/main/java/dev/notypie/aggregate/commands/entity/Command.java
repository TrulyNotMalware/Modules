package dev.notypie.aggregate.commands.entity;


import dev.notypie.global.error.exceptions.CommandErrorCodeImpl;
import dev.notypie.global.error.exceptions.CommandException;
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

    private final Boolean isAvailable;

    @NotBlank
    private final String commandType;

    @NotNull
    private final String publisherId;

    @NotNull
    private final CommandContext context;


    @Builder(builderMethodName = "NewCommand")
    public Command(String appId, @NonNull String commandType, @NonNull Boolean isAvailable,
                   @NonNull String publisherId, @NonNull CommandContext commandContext){
        this.commandId = this.generateIdValue();
        this.appId = appId;
        this.commandType = commandType;
        this.isAvailable = isAvailable;
        this.publisherId = publisherId;
        this.context = commandContext;
    }

    public boolean verifyCommand(){
        return this.verifyCommandAuthorization() && this.verifyCommand();
    }

    private boolean verifyCommandAuthorization(){
        return this.appId != null && !this.appId.isBlank() && this.isAvailable;
    }

    private void verifyCommandValidate(){
        this.context.validateCommand();
    }

    public void executeCommand(){
        if(this.verifyCommand()) this.context.executeCommand();
//        else throw new RuntimeException("Command Verification failed");
        else {
            this.context.sendExceptionResponseToClient(new CommandException(CommandErrorCodeImpl.COMMAND_VERIFICATION_FAILED));
        }
    }

    private String generateIdValue(){
        return UUID.randomUUID().toString();
    }
}
