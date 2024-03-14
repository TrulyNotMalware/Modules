package dev.notypie.aggregate.user.command;

import dev.notypie.command.BaseCommand;
import lombok.Getter;

@Getter
public class UserRoleUpdateCommand extends BaseCommand<Long> {

    private final String role;

    UserRoleUpdateCommand(Long id, String role){
        this.id = id;
        this.role = role;
    }
}
