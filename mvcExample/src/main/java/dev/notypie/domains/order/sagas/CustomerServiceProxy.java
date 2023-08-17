package dev.notypie.domains.order.sagas;

import dev.notypie.domains.order.messaging.commands.ReserveCreditCommand;
import dev.notypie.domains.order.messaging.common.Money;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import org.springframework.stereotype.Component;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

@Component
public class CustomerServiceProxy {
    CommandWithDestination reserveCredit(long orderId, Long customerId, Money orderTotal) {
        return send(new ReserveCreditCommand(customerId, orderTotal, orderId))
                .to("customerService")
                .build();
    }
}
