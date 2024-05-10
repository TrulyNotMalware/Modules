package dev.notypie.infrastructure.impl.command;

import dev.notypie.infrastructure.impl.command.slack.event.SlackCommandExecuteEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
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
//        SagaLifecycle.associateWith("ownerId", appAuthorizeEvent.getOwnerId());
    }
}
