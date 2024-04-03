package dev.notypie.service.app;

import dev.notypie.aggregate.app.dto.AppRegisterDto;
import dev.notypie.aggregate.app.dto.EnableAppDto;

public interface AppService {

    void registerApp(AppRegisterDto appRegisterDto);

    void enableApplication(EnableAppDto enableAppDto);
}
