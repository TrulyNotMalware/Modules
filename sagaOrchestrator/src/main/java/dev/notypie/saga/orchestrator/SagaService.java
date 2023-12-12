package dev.notypie.saga.orchestrator;

import io.eventuate.tram.commands.consumer.CommandWithDestination;

public interface SagaService<SagaData> {

    boolean isLocalParticipants();
    void approve(SagaData data);
    void compensationTransaction(SagaData data);
    CommandWithDestination invokeParticipants(SagaData data);
}
