package dev.notypie.domains.order.dto;

import dev.notypie.messaging.common.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    private Money orderTotal;
    private Long customerId;
}