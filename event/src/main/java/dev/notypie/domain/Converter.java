package dev.notypie.domain;

@Deprecated(forRemoval = true)
public interface Converter<DomainEntity extends AggregateRoot, PersistentEntity> {

    PersistentEntity convert(DomainEntity domain);

    DomainEntity convert(PersistentEntity persistentEntity);
}