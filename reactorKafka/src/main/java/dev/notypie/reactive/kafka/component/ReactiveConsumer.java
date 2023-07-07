package dev.notypie.reactive.kafka.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveConsumer {

    private final ReactiveKafkaConsumerTemplate<String, Object> reactiveKafkaConsumer;

    public Flux<Object> consume(){
        return this.reactiveKafkaConsumer
                .receiveAutoAck()
                // .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
                .doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
                )
                .map(ConsumerRecord::value)
                .doOnNext(ConsumeDto -> log.info("Successfully consumed {}={}",ConsumeDto.getClass().getSimpleName(),ConsumeDto))
                .doOnError(throwable -> log.error("something bad happened. : {}", throwable.getMessage()));
    }
}
