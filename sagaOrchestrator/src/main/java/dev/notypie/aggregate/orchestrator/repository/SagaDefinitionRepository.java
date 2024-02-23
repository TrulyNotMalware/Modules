package dev.notypie.aggregate.orchestrator.repository;

import dev.notypie.aggregate.orchestrator.entity.SagaDefinition;

public interface SagaDefinitionRepository {

    SagaDefinition findById();
}
