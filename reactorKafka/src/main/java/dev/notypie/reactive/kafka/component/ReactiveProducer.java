package dev.notypie.reactive.kafka.component;

import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.observability.micrometer.Micrometer;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveProducer {

    private final ReactiveKafkaProducerTemplate<String, Object> reactiveKafkaProducer;
    private final KafkaSender<String, Object> kafkaSender;
    private final ObservationRegistry observationRegistry;

    public void send(String topic, Object ProducerDto){
        log.info("send to topic={}", topic);
        this.reactiveKafkaProducer.send(topic, ProducerDto)
                .doOnSuccess(voidSenderResult -> log.info("sent {} offset : {}", ProducerDto, voidSenderResult.recordMetadata().offset()))
                .tap(Micrometer.observation(this.observationRegistry))
                .subscribe();
    }

    public void sendTo(String topic, Object ProducerDto){
        log.info("send to topic={}", topic);
        this.kafkaSender.send(Mono.just(SenderRecord.create(new ProducerRecord<>(topic, ProducerDto),null)))
                .doOnError(e -> log.error("Send failed.", e))
                .tap(Micrometer.observation(this.observationRegistry))
                .subscribe(objectSenderResult -> {
                    RecordMetadata metadata = objectSenderResult.recordMetadata();
                    log.info("Message {} sent successfully, topic-partition={}-{} offset={}",
                            objectSenderResult.correlationMetadata(),metadata.topic(), metadata.partition(),metadata.offset());
                });
    }

}
