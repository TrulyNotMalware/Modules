package dev.notypie.domain.event.messaging;

public interface MessageHandler {

    boolean canHandle(Message message);

    void handle(Message message);
}