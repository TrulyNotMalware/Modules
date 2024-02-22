package dev.notypie.aggregate.app.entity;

import dev.notypie.infrastructure.dao.app.jpa.RegisteredApp;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

@Getter
public class App {

    private final String appId;
    private final String appName;
    private final String appType;
    private boolean isAuthenticated;
    private boolean isEnabled;
    private final LocalDateTime registeredDate;

    @Builder(builderMethodName = "toDomainEntity")
    public App(RegisteredApp tableObject){
        this.appId = tableObject.getAppId();
        this.appName = tableObject.getAppName();
        this.appType = tableObject.getAppType();
        this.isAuthenticated = tableObject.isAuthenticated();
        this.isEnabled = tableObject.isEnabled();
        this.registeredDate = tableObject.getCreatedAt();
    }

    @Builder(builderMethodName = "newAppBuilder")
    public App(String appName, String appType){
        this.appId = generateNewAppId();
        this.appName = appName;
        this.appType = appType;
        this.isAuthenticated = false;
        this.isEnabled = false;
        this.registeredDate = LocalDateTime.now();
    }

    public boolean isAvailable(){
        return this.isAuthenticated && this.isEnabled;
    }

    private String generateNewAppId(){
        return "app"+RandomStringUtils.randomAlphanumeric(30);
    }
}
