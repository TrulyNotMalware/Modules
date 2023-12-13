package dev.notypie.aggregate.orchestrator;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "saga_instance")
public class SagaInstance {

    @EmbeddedId
    private SagaId sagaId;

    @JsonProperty("state_name")
    @Column(name = "state_name", length = 100)
    private String stateName;

    @JsonProperty("last_request_id")
    @Column(name = "last_request_id", length = 100)
    private String lastRequestId;

    @JsonProperty("end_state")
    @Column(name = "end_state")
    private int endState;

    @JsonProperty("compensating")
    @Column(name = "compensating")
    private int compensating;

    @JsonProperty("failed")
    @Column(name = "failed")
    private int failed;

    // Class path
    @JsonProperty("saga_data_type")
    @Column(name = "saga_data_type", length = 1000)
    private String sagaDataType;

    @JsonProperty("saga_data_json")
    @Column(name = "saga_data_json", length = 1000)
    private String sagaDataJson;
}
