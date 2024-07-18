package dev.notypie.service.app;


import dev.notypie.domain.app.dto.AppRegisterDto;
import dev.notypie.domain.app.dto.AppResponseDto;
import dev.notypie.domain.app.dto.EnableAppDto;
import dev.notypie.domain.app.entity.App;
import dev.notypie.domain.app.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AppServiceImpl implements AppService{

    private final AppRepository appRepository;

    @Override
    public AppResponseDto registerApp(AppRegisterDto appRegisterDto) {
        App app = this.appRepository.save(App.newAppBuilder()
                .appType(appRegisterDto.getAppType())
                .appName(appRegisterDto.getAppName())
                .build()
        );
        return AppResponseDto.fromDomainEntity().app(app).build();
    }

    @Override
    @Deprecated
    public void enableApplication(EnableAppDto enableAppDto) {

    }
}