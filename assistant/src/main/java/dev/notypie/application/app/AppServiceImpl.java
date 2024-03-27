package dev.notypie.application.app;


import dev.notypie.aggregate.app.dto.AppRegisterDto;
import dev.notypie.aggregate.app.dto.EnableAppDto;
import dev.notypie.aggregate.app.repository.AppRepository;
import dev.notypie.command.app.AppAuthorizeCommand;
import dev.notypie.command.app.AppRegisterCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AppServiceImpl implements AppService{

    private final CommandGateway commandGateway;
    private final AppRepository appRepository;

    @Override
    public void registerApp(AppRegisterDto appRegisterDto) {
        this.commandGateway.send(AppRegisterCommand.builder()
                .appId(appRegisterDto.getAppId()).appName(appRegisterDto.getAppName())
                .appType(appRegisterDto.getAppType()).creatorId(appRegisterDto.getCreatorId())
                .registeredDate(LocalDateTime.now())
                .build());
    }

    @Override
    public void enableApplication(EnableAppDto enableAppDto) {
        this.commandGateway.send(AppAuthorizeCommand.builder()
                .appId(enableAppDto.getAppId()).creatorId(enableAppDto.getCreatorId())
                .build());
    }
}