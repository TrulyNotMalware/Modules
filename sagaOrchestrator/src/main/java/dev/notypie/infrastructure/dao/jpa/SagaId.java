package dev.notypie.infrastructure.dao.jpa;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class SagaId implements Serializable {
    @Serial
    private static final long serialVersionUID = -3185005867338755697L;
    @JsonProperty("saga_type")
    @Column(name = "saga_type")
    private String sagaType;

    @JsonProperty("saga_id")
    @Column(name = "saga_id", length = 100)
    private String sagaId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SagaId sagaId1 = (SagaId) o;
        return Objects.equals(sagaType, sagaId1.sagaType) && Objects.equals(sagaId, sagaId1.sagaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sagaType, sagaId);
    }
}
