package dev.notypie.aggregate.app.repository;

import dev.notypie.aggregate.app.entity.App;

import java.util.Optional;

public interface AppRepository {

    App findByAppIdWithException(String appId);
    Optional<App> findByAppId(String appId);
    App save(App app);
}
