package dev.notypie.command;

import lombok.Getter;

@Getter
public abstract class BaseCommand<Command> {
    public Command id;
}
