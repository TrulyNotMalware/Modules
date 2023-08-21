package dev.notypie.domain.customer.messaging.commands;

import dev.notypie.domain.customer.messaging.common.Money;
import io.eventuate.tram.commands.common.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReserveCreditCommand implements Command {
    private Long orderId;
    private Money orderTotal;
    private long customerId;
}
