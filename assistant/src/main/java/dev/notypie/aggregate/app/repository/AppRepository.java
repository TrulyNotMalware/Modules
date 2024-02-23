package dev.notypie.aggregate.app.repository;

import dev.notypie.aggregate.app.entity.App;

public interface AppRepository {

    App findByAppId(String appId);
    App save(App app);
}
