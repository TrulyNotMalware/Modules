package dev.notypie.domain.customer.saga;

import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerCommandHandlerConfiguration {

    @Bean
    public CommandDispatcher consumerCommandDispatcher(CustomerCommandHandler target,
                                                       SagaCommandDispatcherFactory dispatcherFactory){
        return dispatcherFactory.make("customerCommandDispatcher", target.commandHandlerDefinitions());
    }
}
