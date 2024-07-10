package dev.notypie.domain.user.command;

public interface UserCommandHandler {

    void handleUserRoleChange(UserRoleUpdateCommand command);
}