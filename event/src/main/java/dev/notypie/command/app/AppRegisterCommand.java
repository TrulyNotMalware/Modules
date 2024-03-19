package dev.notypie.command.app;


import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

@Builder
@Getter
public class AppRegisterCommand {
    @TargetAggregateIdentifier
    private String appId;
    private String creatorId;
    private String appName;
    private String appType;
    private LocalDateTime registeredDate;
}