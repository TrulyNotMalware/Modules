package dev.notypie.domain.customer.saga;

import dev.notypie.domain.customer.application.CustomerService;
import dev.notypie.domain.customer.domain.CustomerCreditLimitExceededException;
import dev.notypie.domain.customer.domain.CustomerNotFoundException;
import dev.notypie.domain.customer.messaging.commands.ReserveCreditCommand;
import dev.notypie.domain.customer.messaging.replies.CustomerCreditLimitExceeded;
import dev.notypie.domain.customer.messaging.replies.CustomerCreditReserved;
import dev.notypie.domain.customer.messaging.replies.CustomerNotFound;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

@Component
@RequiredArgsConstructor
public class CustomerCommandHandler {
    private final CustomerService customerService;

    public CommandHandlers commandHandlerDefinitions() {
        return SagaCommandHandlersBuilder
                .fromChannel("customerService")
                .onMessage(ReserveCreditCommand.class, this::reserveCredit)
                .build();
    }

    public Message reserveCredit(CommandMessage<ReserveCreditCommand> cm) {
        ReserveCreditCommand cmd = cm.getCommand();
        try {
            customerService.reserveCredit(cmd.getCustomerId(), cmd.getOrderId(), cmd.getOrderTotal());
            return withSuccess(new CustomerCreditReserved());
        } catch (CustomerNotFoundException e) {
            return withFailure(new CustomerNotFound());
        } catch (CustomerCreditLimitExceededException e) {
            return withFailure(new CustomerCreditLimitExceeded());
        }
    }
}
