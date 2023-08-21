package dev.notypie.saga.orchestrator;

import io.eventuate.tram.sagas.spring.orchestration.SagaOrchestratorConfiguration;
import io.eventuate.tram.spring.commands.common.TramCommandsCommonAutoConfiguration;
import io.eventuate.tram.spring.consumer.jdbc.TramConsumerJdbcAutoConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.messaging.common.TramMessagingCommonAutoConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SagaOrchestratorConfiguration.class,//SagaInstanceFactory
        TramMessagingCommonAutoConfiguration.class,//ChannelMapping
        TramMessageProducerJdbcConfiguration.class,//MessageProducerImplementation
        TramCommandsCommonAutoConfiguration.class,//CommandNameMapping
        EventuateTramKafkaMessageConsumerConfiguration.class,//MessageConsumer
        TramConsumerJdbcAutoConfiguration.class//DuplicateMessageDetector
})
public class AutoConfiguration {
}
