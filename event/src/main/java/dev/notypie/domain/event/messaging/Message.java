package dev.notypie.domain.event.messaging;

public record Message(String id, String processId, String initiator, String predecessorId, Object data) {
}