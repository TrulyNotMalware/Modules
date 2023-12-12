package dev.notypie.saga.orchestrator;

import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;

public class SagaOrchestrator<T> implements SimpleSaga<T> {

    private final SagaService<T> sagaService;
    private final SagaDefinition<T> sagaDefinition;

    public SagaOrchestrator(SagaService<T> sagaService, SagaDefinition<T> sagaDefinition){
        this.sagaService = sagaService;
        this.sagaDefinition = sagaDefinition;
    }

    @Override
    public SagaDefinition<T> getSagaDefinition() {
        return this.sagaDefinition;
    }
}
