package dev.notypie.event.auth;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRoleCheckedEvent {
    // Unique Property
    private String userId;
    private String userRole;
}
