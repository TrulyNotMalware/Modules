package dev.notypie.aggregate.orchestrator.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SagaDefinition {

    private final SagaId sagaId;


    @Builder()
    public SagaDefinition(String sagaType, String sagaId){
        this.sagaId = new SagaId(sagaType, sagaId);
    }
}