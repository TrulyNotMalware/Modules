package dev.notypie.infrastructure.dao.app.jpa;

import dev.notypie.domain.app.entity.App;
import dev.notypie.domain.app.repository.AppRepository;
import dev.notypie.infrastructure.dao.app.jpa.schema.RegisteredApp;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class JpaAppRepositoryImpl implements AppRepository {

    private final RegisteredAppRepository repository;

    @Override
    public App findByAppIdWithException(String appId) {
        return null;
    }

    @Override
    public Optional<App> findByAppId(String appId) {
        return repository.findById(appId).map(RegisteredApp::toDomainEntity);
    }

    @Override
    public App save(App app) {
        RegisteredApp tableEntity = RegisteredApp.toTable().app(app).build();
        return this.repository.save(tableEntity).toDomainEntity();
    }
}
