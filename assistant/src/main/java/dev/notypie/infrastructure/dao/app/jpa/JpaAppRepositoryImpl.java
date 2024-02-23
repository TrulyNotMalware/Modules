package dev.notypie.infrastructure.dao.app.jpa;

import dev.notypie.aggregate.app.entity.App;
import dev.notypie.aggregate.app.repository.AppRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaAppRepositoryImpl implements AppRepository {

    private final RegisteredAppRepository repository;

    @Override
    public App findByAppId(String appId) {
        return repository.findById(appId).orElseThrow().toDomainEntity();
    }

    @Override
    public App save(App app) {
        RegisteredApp tableEntity = RegisteredApp.toTable().app(app).build();
        return this.repository.save(tableEntity).toDomainEntity();
    }
}
