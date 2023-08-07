package dev.notypie.domains.order.application;

import dev.notypie.domains.order.domain.Order;
import dev.notypie.domains.order.domain.OrderRepository;
import dev.notypie.domains.order.messaging.common.OrderDetails;
import dev.notypie.domains.order.messaging.common.RejectionReason;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order createOrder(OrderDetails orderDetails) {
        Order order = Order.createOrder(orderDetails);
        this.orderRepository.save(order);
        return order;
    }

    public void approveOrder(Long orderId) {
        this.orderRepository.findById(orderId).get().approve();
    }

    public void rejectOrder(Long orderId, RejectionReason rejectionReason) {
        this.orderRepository.findById(orderId).get().reject(rejectionReason);
    }
}
