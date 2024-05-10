package dev.notypie.service.app;


import dev.notypie.aggregate.app.dto.AppRegisterDto;
import dev.notypie.aggregate.app.dto.AppResponseDto;
import dev.notypie.aggregate.app.dto.EnableAppDto;
import dev.notypie.aggregate.app.entity.App;
import dev.notypie.aggregate.app.repository.AppRepository;
import dev.notypie.command.app.AppAuthorizeCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AppServiceImpl implements AppService{

    private final CommandGateway commandGateway;
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
    public void enableApplication(EnableAppDto enableAppDto) {
        this.commandGateway.send(AppAuthorizeCommand.builder()
                .appId(enableAppDto.getAppId()).creatorId(enableAppDto.getCreatorId())
                .build());
    }
}