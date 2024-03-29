package dev.notypie.infrastructure.impl.app;

import dev.notypie.command.app.AppEnableCommand;
import dev.notypie.command.user.UserRoleCheckCommand;
import dev.notypie.event.app.AppAuthorizeEvent;
import dev.notypie.event.app.AppRegisteredCompletedEvent;
import dev.notypie.event.auth.UserInsufficientPrivilegesEvent;
import dev.notypie.event.auth.UserRoleCheckedEvent;
import dev.notypie.global.constants.Constants;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandExecutionException;
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
//@RequiredArgsConstructor
public class AppAxonSagaManager {

    //must create by NoArgsConstructor.
    private transient CommandGateway commandGateway;

    //Setter injection
    @Autowired
    public void setCommandGateway(CommandGateway commandGateway){
        this.commandGateway = commandGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "transactionId")
    public void on(AppAuthorizeEvent appAuthorizeEvent) {
        log.info("create Saga instance {}", appAuthorizeEvent);
        SagaLifecycle.associateWith("ownerId", appAuthorizeEvent.getOwnerId());
        try{
            this.commandGateway.send(UserRoleCheckCommand.builder()
                    .userId(appAuthorizeEvent.getOwnerId()).requiredRole(Constants.USER_ROLE_ADMIN).build());
        }catch (CommandExecutionException e){
            log.error("command exceptions");
        }
    }

    @SagaEventHandler(associationProperty = "userId")
    public void on(UserRoleCheckedEvent userRoleCheckedEvent){
        log.info("Authenticated Role {}", Constants.USER_ROLE_ADMIN);
        this.commandGateway.send(AppEnableCommand.builder()
                .appId(userRoleCheckedEvent.getUserId())
                .build());
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "appId")
    public void on(AppRegisteredCompletedEvent completedEvent){
        log.info("App {} successfully registered.", completedEvent.getAppId());
    }

    @SagaEventHandler(associationProperty = "userId")
    public void on(UserInsufficientPrivilegesEvent userInsufficientPrivilegesEvent){
        log.error("InsufficientPrivileges detected. End Saga");
        SagaLifecycle.end();
    }
}