package dev.notypie.kafka.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;


@Configuration
@RequiredArgsConstructor
@EnableKafka
public class KafkaConsumerConfig {

    private final CustomKafkaListenerObservationConvention convention;
    private final static String TOPIC = "examples";

    @Bean
    public ConsumerFactory<String, Object> consumerFactory(KafkaProperties kafkaProperties){
        return new DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties());
    }

    /**
     * For @kafkaListener, Concurrent KafkaListener Container beans.
     * @param kafkaProperties KafkaProperties
     * @return Factory
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> concurrentKafkaListenerContainerFactory(KafkaProperties kafkaProperties){
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setObservationEnabled(true);
        factory.getContainerProperties().setMicrometerEnabled(false);//Micrometer timer disable when setObservation is true.
        factory.getContainerProperties().setObservationConvention(this.convention);
        factory.setConsumerFactory(consumerFactory(kafkaProperties));
        return factory;
    }

    /**
     * check usage for this & topic & Replying Kafka
     */
    @Bean
    public ConcurrentMessageListenerContainer<String, Object> concurrentMessageListenerContainer(
            ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory){
        ConcurrentMessageListenerContainer<String, Object> messageListenerContainer = containerFactory.createContainer(TOPIC);
        messageListenerContainer.getContainerProperties().setObservationEnabled(true);
        messageListenerContainer.setAutoStartup(true);
        return messageListenerContainer;
    }

    @Bean
    public ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate(
            ProducerFactory<String, Object> producerFactory, ConcurrentMessageListenerContainer<String, Object> messageListenerContainer){
        ReplyingKafkaTemplate<String, Object, Object> replyingKafkaTemplate = new ReplyingKafkaTemplate<>(producerFactory, messageListenerContainer);
        replyingKafkaTemplate.setObservationEnabled(true);
        return replyingKafkaTemplate;
    }

}
