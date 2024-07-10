package dev.notypie.domain.event.eventsourcing;

import java.lang.reflect.Type;

public interface EventReader {

    Iterable<Object> queryEvents(
            Type stateType,
            String streamId,
            long fromVersion);

    default Iterable<Object> queryEvents(Type stateType, String streamId) {
        return queryEvents(stateType, streamId, 1);
    }
} 