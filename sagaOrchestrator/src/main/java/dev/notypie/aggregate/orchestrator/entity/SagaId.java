package dev.notypie.aggregate.orchestrator.entity;

import lombok.Getter;

@Getter
class SagaId {

    private final String sagaType;
    private final String sagaId;

    SagaId(String sagaType, String sagaId){
        this.sagaId = sagaId;
        this.sagaType = sagaType;
    }


}
