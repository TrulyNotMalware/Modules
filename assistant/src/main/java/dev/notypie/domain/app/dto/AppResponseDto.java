package dev.notypie.domain.app.dto;

import dev.notypie.domain.app.entity.App;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class AppResponseDto {
    private final String appId;
    private final String appName;
    private final String appType;
    private final String creatorId;
    private final String registeredDate;

    @Builder(builderMethodName = "fromDomainEntity")
    public AppResponseDto(App app){
        this.appId = app.getAppId();
        this.appName = app.getAppName();
        this.appType = app.getAppType();
        this.creatorId = app.getCreatorId();
        this.registeredDate = app.getRegisteredDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
