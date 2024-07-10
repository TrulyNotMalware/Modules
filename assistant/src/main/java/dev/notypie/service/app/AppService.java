package dev.notypie.service.app;

import dev.notypie.domain.app.dto.AppRegisterDto;
import dev.notypie.domain.app.dto.AppResponseDto;
import dev.notypie.domain.app.dto.EnableAppDto;

public interface AppService {

    AppResponseDto registerApp(AppRegisterDto appRegisterDto);

    void enableApplication(EnableAppDto enableAppDto);
}
