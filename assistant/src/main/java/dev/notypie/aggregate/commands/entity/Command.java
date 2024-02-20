package dev.notypie.aggregate.commands.entity;


import lombok.Builder;

public class Command {

    private final Long commandId;
    private final String appId;
    private String eventType;
    private final Long publisherId;

    private CommandContext context;

    @Builder
    public Command(Long commandId, String appId, Long publisherId, String eventType){
        if(commandId == null) this.commandId = 0L;
        else this.commandId = commandId;
        this.appId = appId;
        this.publisherId = publisherId;
        this.eventType = eventType;
    }



}
