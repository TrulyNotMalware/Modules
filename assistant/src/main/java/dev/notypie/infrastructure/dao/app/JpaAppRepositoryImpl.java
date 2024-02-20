package dev.notypie.infrastructure.dao.app;

import dev.notypie.aggregate.app.entity.App;
import dev.notypie.aggregate.app.repository.AppRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaAppRepositoryImpl implements AppRepository {


    @Override
    public App findByAppId(String appId) {
        return null;
    }

    @Override
    public App save(App app) {
        return null;
    }
}
