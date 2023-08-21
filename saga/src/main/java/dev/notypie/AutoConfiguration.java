package dev.notypie;

import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import io.eventuate.tram.spring.commands.common.TramCommandsCommonAutoConfiguration;
import io.eventuate.tram.spring.consumer.jdbc.TramConsumerJdbcAutoConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.messaging.common.TramMessagingCommonAutoConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({OptimisticLockingDecoratorConfiguration.class,
        SagaParticipantConfiguration.class,//SagaCommandDispatcherFactory
        TramMessageProducerJdbcConfiguration.class,//MessageProducerImplementation
        TramCommandsCommonAutoConfiguration.class,//CommandNameMapping
        TramMessagingCommonAutoConfiguration.class,//ChannelMapping
        EventuateTramKafkaMessageConsumerConfiguration.class,//MessageConsumer
        TramConsumerJdbcAutoConfiguration.class//DuplicateMessageDetector
})
public class AutoConfiguration {
}
