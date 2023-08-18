package dev.notypie.domains.order.sagas;

import dev.notypie.domains.order.domain.Order;
import dev.notypie.domains.order.domain.OrderRepository;
import dev.notypie.domains.order.messaging.common.OrderDetails;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import io.eventuate.tram.sagas.spring.orchestration.SagaOrchestratorConfiguration;
import io.eventuate.tram.spring.commands.common.TramCommandsCommonAutoConfiguration;
import io.eventuate.tram.spring.commands.producer.TramCommandProducerConfiguration;
import io.eventuate.tram.spring.consumer.common.TramConsumerBaseCommonConfiguration;
import io.eventuate.tram.spring.consumer.jdbc.TramConsumerJdbcAutoConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.messaging.common.TramMessagingCommonAutoConfiguration;
import io.eventuate.tram.spring.messaging.producer.common.TramMessagingCommonProducerConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Instead of explicitly @Import-ing configuration classes you can rely on the auto-configuration provided by eventuate-tram-sagas-spring-orchestration-simple-dsl-starter
@Service
@RequiredArgsConstructor
@Import({SagaOrchestratorConfiguration.class,//SagaInstanceFactory
        TramMessagingCommonAutoConfiguration.class,//ChannelMapping
        TramMessageProducerJdbcConfiguration.class,//MessageProducerImplementation
        TramCommandsCommonAutoConfiguration.class,//CommandNameMapping
        EventuateTramKafkaMessageConsumerConfiguration.class,//MessageConsumer
        TramConsumerJdbcAutoConfiguration.class//DuplicateMessageDetector
})
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
