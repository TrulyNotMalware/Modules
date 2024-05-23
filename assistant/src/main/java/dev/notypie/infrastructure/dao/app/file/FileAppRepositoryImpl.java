package dev.notypie.infrastructure.dao.app.file;

import dev.notypie.aggregate.app.entity.App;
import dev.notypie.aggregate.app.repository.AppRepository;

import java.util.Optional;

//FIXME Not Implemented Yet
public class FileAppRepositoryImpl implements AppRepository {

    @Override
    public App findByAppIdWithException(String appId) {
        return null;
    }

    @Override
    public Optional<App> findByAppId(String appId) {
        return Optional.empty();
    }

    @Override
    public App save(App app) {
        return null;
    }
}
