package dev.notypie.infrastructure.impl.command.slack;

import dev.notypie.infrastructure.impl.command.slack.commands.ExecuteSlackCommand;
import dev.notypie.infrastructure.impl.command.slack.events.SlackCommandExecuteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Component
@RequiredArgsConstructor
public class AxonSlackCommandHandler {

    @CommandHandler
    protected void executeSlackCommands(ExecuteSlackCommand command){
        log.info("execute slack command");
        apply(SlackCommandExecuteEvent.builder().transactionId(UUID.randomUUID().toString())
                .appId(command.getAppId()).event(command.getEvent())
                .build());
    }
}
