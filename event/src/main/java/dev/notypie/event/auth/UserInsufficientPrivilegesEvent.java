package dev.notypie.event.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInsufficientPrivilegesEvent {
    private String userId;
    private String userRole;
    private String requiredRole;
    private String description;
}