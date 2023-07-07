package dev.notypie.kafka.configurations;

import io.micrometer.common.KeyValues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.micrometer.KafkaListenerObservation;
import org.springframework.kafka.support.micrometer.KafkaListenerObservationConvention;
import org.springframework.kafka.support.micrometer.KafkaRecordReceiverContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomKafkaListenerObservationConvention implements KafkaListenerObservationConvention {

    @Override
    public KeyValues getLowCardinalityKeyValues(KafkaRecordReceiverContext context) {
        return KeyValues.of(KafkaListenerObservation.ListenerLowCardinalityTags.LISTENER_ID.asString(),
                context.getListenerId());
    }

    @Override
    public String getContextualName(KafkaRecordReceiverContext context) {
        return context.getSource() + " receive";
    }

    @Override
    public String getName() {
        return "spring.kafka.listener";
    }
}