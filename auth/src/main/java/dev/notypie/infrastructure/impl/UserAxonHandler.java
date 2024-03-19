package dev.notypie.infrastructure.impl;

import dev.notypie.aggregate.user.entity.User;
import dev.notypie.aggregate.user.repository.UserDomainRepository;
import dev.notypie.command.user.UserRoleCheckCommand;
import dev.notypie.event.auth.UnknownUserDetectedEvent;
import dev.notypie.event.auth.UserInsufficientPrivilegesEvent;
import dev.notypie.event.auth.UserRoleCheckedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAxonHandler {

    private final UserDomainRepository repository;

    @CommandHandler
    protected void handle(UserRoleCheckCommand userRoleCheckCommand){
        try{
            log.info("User check command");
            User user = repository.findByUserId(userRoleCheckCommand.getUserId());
            if(user.roleRequired(userRoleCheckCommand.getRequiredRole())){
                apply(UserRoleCheckedEvent.builder()
                        .userId(user.getUserId())
                        .userRole(user.getRole()).build());
            }
            else{
                apply(UserInsufficientPrivilegesEvent.builder()
                        .userId(user.getUserId()).userRole(user.getRole())
                        .requiredRole(userRoleCheckCommand.getRequiredRole())
                        .description("Insufficient Privileges")
                        .build());
            }
        }catch( Exception e){
            log.error("UserRoleCheckCommand got exception");
            apply(UnknownUserDetectedEvent.builder()
                    .userId(userRoleCheckCommand.getUserId())
                    .description("Unknown User detected.")
                    .build());
        }

    }
}
