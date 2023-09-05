package dev.notypie.domains.order.sagas;

import dev.notypie.messaging.common.OrderDetails;
import dev.notypie.messaging.common.RejectionReason;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateOrderSagaData {

    private OrderDetails orderDetails;
    private Long orderId;
    private RejectionReason rejectionReason;

    public CreateOrderSagaData(OrderDetails orderDetails){
        this.orderDetails = orderDetails;
    }
}
