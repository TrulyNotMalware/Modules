package dev.notypie.domains.order.sagas;

import dev.notypie.domains.order.domain.Order;
import dev.notypie.domains.order.domain.OrderRepository;
import dev.notypie.messaging.common.OrderDetails;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Instead of explicitly @Import-ing configuration classes you can rely on the auto-configuration provided by eventuate-tram-sagas-spring-orchestration-simple-dsl-starter
@Service
@RequiredArgsConstructor
public class OrderSagaService {
    private final OrderRepository orderRepository;
    private final SagaInstanceFactory sagaInstanceFactory;
    private final CreateOrderSaga createOrderSaga;

    @Transactional
    public Order createOrder(OrderDetails orderDetails){
        CreateOrderSagaData data = new CreateOrderSagaData(orderDetails);
        this.sagaInstanceFactory.create(createOrderSaga, data);
        return this.orderRepository.findById(data.getOrderId()).get();
    }
}
