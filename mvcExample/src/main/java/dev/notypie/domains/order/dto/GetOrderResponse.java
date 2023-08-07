package dev.notypie.domains.order.dto;

import dev.notypie.domains.order.messaging.common.OrderState;
import dev.notypie.domains.order.messaging.common.RejectionReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponse {
    private Long orderId;
    private OrderState orderState;
    private RejectionReason rejectionReason;
}
