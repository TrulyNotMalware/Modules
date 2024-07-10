package dev.notypie.domain.event.eventsourcing;

public record Snapshot<S>(String streamId, long version, S state) {
    static <S> Snapshot<S> seed(String streamId, S seed) {
        return new Snapshot<>(streamId, 0, seed);
    }

    Snapshot<S> next(S newState) {
        return new Snapshot<>(streamId, version + 1, newState);
    }
}