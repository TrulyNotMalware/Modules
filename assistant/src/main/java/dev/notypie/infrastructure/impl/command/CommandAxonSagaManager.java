package dev.notypie.infrastructure.impl.command;

import dev.notypie.infrastructure.impl.app.commands.VerifySlackAppCommand;
import dev.notypie.infrastructure.impl.app.events.AppNotRegisteredEvent;
import dev.notypie.infrastructure.impl.command.slack.events.SlackCommandExecuteEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Saga
@NoArgsConstructor
public class CommandAxonSagaManager {
    private transient CommandGateway commandGateway;

    //Setter injection
    @Autowired
    public void setCommandGateway(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(SlackCommandExecuteEvent executeSlackEvent){
        log.info("create Saga instance {}", executeSlackEvent);
        SagaLifecycle.associateWith("transactionId", executeSlackEvent.getTransactionId());
        this.commandGateway.send(VerifySlackAppCommand.builder()
                .appId(executeSlackEvent.getAppId()).event(executeSlackEvent.getEvent())
                .creatorId(executeSlackEvent.getEvent().userId).build());
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "appId")
    public void onAppNoRegistered(AppNotRegisteredEvent appNotRegisteredEvent){
        log.info("App not registered yet.");
        appNotRegisteredEvent.getOrigin();
    }
}