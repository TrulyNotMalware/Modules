package dev.notypie.infrastructure.impl.command.slack;

import dev.notypie.infrastructure.impl.command.slack.commands.CreateSlackCommand;
import dev.notypie.infrastructure.impl.command.slack.commands.ExecuteSlackCommand;
import dev.notypie.infrastructure.impl.command.slack.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.events.CreateSlackCommandEvent;
import dev.notypie.infrastructure.impl.command.slack.events.SlackCommandExecuteEvent;
import dev.notypie.infrastructure.impl.command.slack.events.SlackEvent;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Getter
@Aggregate
@NoArgsConstructor
public class SlackDomainCommand {

    @AggregateIdentifier
    private String commandId;

    @NotBlank
    private String appId;

    private SlackEvent<SlackContext> event;

    @CommandHandler
    public SlackDomainCommand(CreateSlackCommand createSlackCommand){
        log.info("execute slack command");
        apply(CreateSlackCommandEvent.builder().commandId(UUID.randomUUID().toString())
                .appId(createSlackCommand.getAppId()).event(createSlackCommand.getEvent())
                .build());
    }

    @EventSourcingHandler
    protected void on(CreateSlackCommandEvent createSlackCommandEvent){
        this.commandId = createSlackCommandEvent.getCommandId();
        this.appId = createSlackCommandEvent.getAppId();
        this.event = createSlackCommandEvent.getEvent();
    }
}