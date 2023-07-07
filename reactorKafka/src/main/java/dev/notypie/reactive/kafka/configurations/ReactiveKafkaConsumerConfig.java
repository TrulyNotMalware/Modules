package dev.notypie.reactive.kafka.configurations;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.MicrometerConsumerListener;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ReactiveKafkaConsumerConfig {

    private final MeterRegistry registry;
    private static final String TOPIC = "examples";


    @Bean
    public ReceiverOptions<String, Object> kafkaReceiverOptions(KafkaProperties kafkaProperties){
        ReceiverOptions<String, Object> basicReceiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        basicReceiverOptions.consumerListener(new MicrometerConsumerListener(this.registry));
        return basicReceiverOptions.subscription(Collections.singletonList(TOPIC));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, Object> reactiveKafkaConsumerTemplate(ReceiverOptions<String, Object> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<String, Object>(kafkaReceiverOptions);
    }

    @Bean
    public KafkaReceiver<String, Object> kafkaReceiver(KafkaProperties kafkaProperties){
        ReceiverOptions<String, Object> basicReceiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        basicReceiverOptions.consumerListener(new MicrometerConsumerListener(this.registry));
        return KafkaReceiver.create(basicReceiverOptions);
    }
}
