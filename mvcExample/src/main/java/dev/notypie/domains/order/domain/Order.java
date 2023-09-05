package dev.notypie.domains.order.domain;

import dev.notypie.messaging.common.OrderDetails;
import dev.notypie.messaging.common.OrderState;
import dev.notypie.messaging.common.RejectionReason;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name="orders")
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    @Embedded
    private OrderDetails orderDetails;

    @Enumerated(EnumType.STRING)
    private RejectionReason rejectionReason;

    @Version
    private Long version;

    public Order(OrderDetails orderDetails){//Initialize state to Pending.
        this.orderDetails = orderDetails;
        this.state = OrderState.PENDING;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void approve() {
        this.state = OrderState.APPROVED;
    }

    public void reject(RejectionReason rejectionReason) {
        this.state = OrderState.REJECTED;
        this.rejectionReason = rejectionReason;
    }
    public static Order createOrder(OrderDetails orderDetails) {
        return new Order(orderDetails);
    }


}