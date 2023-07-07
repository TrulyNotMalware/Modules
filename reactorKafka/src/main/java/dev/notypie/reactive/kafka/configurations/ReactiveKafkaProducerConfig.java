package dev.notypie.reactive.kafka.configurations;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.MicrometerProducerListener;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ReactiveKafkaProducerConfig {

    private final MeterRegistry registry;

    @Bean
    public ReactiveKafkaProducerTemplate<String, Object> reactiveKafkaProducerTemplate(KafkaProperties properties){
        Map<String, Object> props = properties.buildProducerProperties();
        SenderOptions<String, Object> options = SenderOptions.create(props);
        options.producerListener(new MicrometerProducerListener(this.registry));
        return new ReactiveKafkaProducerTemplate<String, Object>(options);
    }

    @Bean
    public KafkaSender<String, Object> kafkaSender(KafkaProperties properties){
        Map<String, Object> props = properties.buildProducerProperties();
        SenderOptions<String, Object> options = SenderOptions.create(props);
        options.producerListener(new MicrometerProducerListener(this.registry));
        return KafkaSender.create(options);
    }


}
