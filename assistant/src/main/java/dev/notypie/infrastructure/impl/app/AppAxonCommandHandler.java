package dev.notypie.infrastructure.impl.app;

import dev.notypie.domain.app.repository.AppRepository;
import dev.notypie.command.app.AppAuthorizeCommand;
import dev.notypie.command.app.AppEnableCommand;
import dev.notypie.event.app.AppAuthorizeEvent;
import dev.notypie.event.app.AppRegisteredCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppAxonCommandHandler {

    private final AppRepository appRepository;


    @CommandHandler
    protected void enableAppCommand(AppAuthorizeCommand appAuthorizeCommand){
        log.info("handling {}", appAuthorizeCommand);
        apply(AppAuthorizeEvent.builder().transactionId(UUID.randomUUID().toString())
                .appId(appAuthorizeCommand.getAppId()).ownerId(appAuthorizeCommand.getCreatorId()).build());
    }

    @CommandHandler
    protected void enableApp(AppEnableCommand enableCommand){
        log.info("handling {}", enableCommand);
//        this.isEnabled = true;
        apply(AppRegisteredCompletedEvent.builder()
                .appId(enableCommand.getAppId()).authenticatedDate(LocalDateTime.now())
                .build());
    }
}
