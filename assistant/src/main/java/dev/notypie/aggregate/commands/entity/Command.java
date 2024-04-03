package dev.notypie.aggregate.commands.entity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;


@Getter
public class Command {

    @NotNull
    private final Long commandId;

    @NotBlank
    private final String appId;

    @NotBlank
    private final String commandType;
    private final Long publisherId;
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

    public boolean isSupportCommand(String commandType){
        return this.commandType.equals(commandType);
    }

    private Long generateIdValue(){
        return 0L;
    }
}
