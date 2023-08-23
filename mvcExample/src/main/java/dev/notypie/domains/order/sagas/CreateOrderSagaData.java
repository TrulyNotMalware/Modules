package dev.notypie.domains.order.sagas;

import dev.notypie.messaging.common.OrderDetails;
import dev.notypie.messaging.common.RejectionReason;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderSagaData {

    private OrderDetails orderDetails;
    private Long orderId;
    private RejectionReason rejectionReason;

    public CreateOrderSagaData(OrderDetails orderDetails){
        this.orderDetails = orderDetails;
    }
}
