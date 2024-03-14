package dev.notypie.aggregate.user.command;

public interface UserCommandHandler {

    void handleUserRoleChange(UserRoleUpdateCommand command);
}