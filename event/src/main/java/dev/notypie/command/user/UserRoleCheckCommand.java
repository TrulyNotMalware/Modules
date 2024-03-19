package dev.notypie.command.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@ToString
@Getter
@Builder
public class UserRoleCheckCommand {
    @TargetAggregateIdentifier
    private String userId;
    private String requiredRole;
}
