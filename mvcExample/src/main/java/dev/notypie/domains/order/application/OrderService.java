package dev.notypie.domains.order.application;

import dev.notypie.domains.order.domain.Order;
import dev.notypie.domains.order.domain.OrderRepository;
import dev.notypie.messaging.common.OrderDetails;
import dev.notypie.messaging.common.RejectionReason;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order createOrder(OrderDetails orderDetails) {
        Order order = Order.createOrder(orderDetails);
        this.orderRepository.save(order);
        Order find = this.orderRepository.findById(orderDetails.getCustomerId()).orElseThrow();
        log.info("order selected : {}",find.getOrderDetails());
        return order;
    }

    public void approveOrder(Long orderId) {
        this.orderRepository.findById(orderId).get().approve();
    }

    public void rejectOrder(Long orderId, RejectionReason rejectionReason) {
        this.orderRepository.findById(orderId).get().reject(rejectionReason);
    }
}
