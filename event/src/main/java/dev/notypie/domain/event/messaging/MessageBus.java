package dev.notypie.domain.event.messaging;

import static java.util.Arrays.asList;

public interface MessageBus {

    void send(String partitionKey, Iterable<MessageHandler> messages);

    default void send(String partitionKey, MessageHandler message) {
        send(partitionKey, asList(message));
    }
}