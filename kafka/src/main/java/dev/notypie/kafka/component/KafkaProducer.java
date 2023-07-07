package dev.notypie.kafka.component;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Observed(name = "kafkaProducer")
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, Object ProducerDto){
        log.info("Produce Messages");
        kafkaTemplate.send(topic, ProducerDto);
    }

}
