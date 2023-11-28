package dev.notypie.domains.order.sagas;

import dev.notypie.domains.order.application.OrderService;
import dev.notypie.domains.order.domain.Order;
import dev.notypie.messaging.common.Money;
import dev.notypie.messaging.common.RejectionReason;
import dev.notypie.domains.order.sagas.exceptions.CustomerCreditLimitExceeded;
import dev.notypie.domains.order.sagas.exceptions.CustomerNotFound;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaData> {

    private final OrderService orderService;
    private final CustomerServiceProxy customerServiceProxy;

    private final SagaDefinition<CreateOrderSagaData> sagaDefinition =
            step()
                    .invokeLocal(this::create)
                    .withCompensation(this::reject)
                    .step()
                    .invokeParticipant(this::reserveCredit)
                    .onReply(CustomerNotFound.class, this::handleCustomerNotFound)
                    .onReply(CustomerCreditLimitExceeded.class, this::handleCustomerCreditLimitExceeded)
                    .step()
                    .invokeLocal(this::approve)
                    .build();

    @Override
    public SagaDefinition<CreateOrderSagaData> getSagaDefinition() {
        return this.sagaDefinition;
    }

    private void create(CreateOrderSagaData data) {
        Order order = orderService.createOrder(data.getOrderDetails());
        data.setOrderId(order.getId());
    }

    private void reject(CreateOrderSagaData data) {
        this.orderService.rejectOrder(data.getOrderId(), data.getRejectionReason());
    }

    private CommandWithDestination reserveCredit(CreateOrderSagaData data) {
        long orderId = data.getOrderId();
        Long customerId = data.getOrderDetails().getCustomerId();
        Money orderTotal = data.getOrderDetails().getOrderTotal();
        return customerServiceProxy.reserveCredit(orderId, customerId, orderTotal);
    }

    private void handleCustomerNotFound(CreateOrderSagaData data, CustomerNotFound reply) {
        log.info("Customer Not Found");
        data.setRejectionReason(RejectionReason.UNKNOWN_CUSTOMER);
    }

    private void handleCustomerCreditLimitExceeded(CreateOrderSagaData data, CustomerCreditLimitExceeded reply) {
        log.info("setRejectReason : INSUFFICIENT");
        data.setRejectionReason(RejectionReason.INSUFFICIENT_CREDIT);
    }

    private void approve(CreateOrderSagaData data) {
        orderService.approveOrder(data.getOrderId());
    }
}
