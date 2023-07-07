package dev.notypie.kafka.component;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@Observed(name = "replyingKafka")
@RequiredArgsConstructor
public class ReplyingKafka {
    private final ReplyingKafkaTemplate<String, Object, Object> template;

    public ConsumerRecord<String, Object> sendAndReceive(String topic, Object message, String replyTopic) throws ExecutionException, InterruptedException {
        ProducerRecord<String, Object> record = new ProducerRecord<>(topic, message);
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC,replyTopic.getBytes(StandardCharsets.UTF_8)));
        RequestReplyFuture<String, Object, Object> replyFuture = this.template.sendAndReceive(record);
        return replyFuture.get();
    }
}
