package dev.notypie.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;

@Slf4j
@RequiredArgsConstructor
public abstract class AxonCommandHandler {

    private transient final CommandGateway commandGateway;

}
