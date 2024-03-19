package dev.notypie.application.app;

import dev.notypie.aggregate.app.dto.AppRegisterDto;

public interface AppService {

    void registerApp(AppRegisterDto appRegisterDto);
}
