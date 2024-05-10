package dev.notypie.infrastructure.impl.command.slack;

import dev.notypie.infrastructure.impl.command.slack.commands.ExecuteSlackCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Component
@RequiredArgsConstructor
public class AxonSlackCommandHandler {

    @CommandHandler
    protected void executeSlackCommands(ExecuteSlackCommand command){
        log.info("execute slack command");
        //FIXME Axon external Command handler from domain entity.
        //DomainEntity must be POJO.
//        apply();
    }
}
