package dev.notypie.domains.order.sagas;

import dev.notypie.messaging.commands.ReserveCreditCommand;
import dev.notypie.messaging.common.Money;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import org.springframework.stereotype.Component;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

@Component
public class CustomerServiceProxy {
    CommandWithDestination reserveCredit(long orderId, Long customerId, Money orderTotal) {
        return send(new ReserveCreditCommand(orderId, orderTotal, customerId))
                .to("customerService")
                .build();
    }
}
