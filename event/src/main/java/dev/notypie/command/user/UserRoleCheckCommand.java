package dev.notypie.command.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class UserRoleCheckCommand {
    private String userId;
    private String requiredRole;
}
