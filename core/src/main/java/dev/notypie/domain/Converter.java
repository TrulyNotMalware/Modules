package dev.notypie.domain;

public interface Converter<DomainEntity extends AggregateRoot, PersistentEntity> {

    PersistentEntity convert(DomainEntity domain);

    DomainEntity convert(PersistentEntity persistentEntity);
}