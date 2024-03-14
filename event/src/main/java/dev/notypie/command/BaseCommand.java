package dev.notypie.command;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
public abstract class BaseCommand<Command> {

    @TargetAggregateIdentifier
    public Command id;
}
