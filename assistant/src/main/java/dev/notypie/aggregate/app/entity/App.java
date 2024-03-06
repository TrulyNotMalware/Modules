package dev.notypie.aggregate.app.entity;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

@Getter
public class App {

    private final String appId;
    private final String appName;
    private final String appType;
    private final boolean isAuthenticated;
    private final boolean isEnabled;
    private final boolean isEnterprise;
    private final LocalDateTime registeredDate;

    @Builder(builderMethodName = "newAppBuilder")
    public App(String appName, String appType){
        this.appId = generateNewAppId();
        this.appName = appName;
        this.appType = appType;
        this.isAuthenticated = false;
        this.isEnabled = false;
        this.isEnterprise = false;
        this.registeredDate = LocalDateTime.now();
    }

    @Builder(builderMethodName = "toEntity")
    public App(String appId, String appName, String appType,
               boolean isAuthenticated, boolean isEnabled, boolean isEnterprise, LocalDateTime registeredDate){
        this.appId = appId;
        this.appName = appName;
        this.appType = appType;
        this.isAuthenticated = isAuthenticated;
        this.isEnabled = isEnabled;
        this.isEnterprise = isEnterprise;
        this.registeredDate = registeredDate;
    }

    public boolean isAvailable(){
        return this.isAuthenticated && this.isEnabled;
    }

    private String generateNewAppId(){
        return "app"+RandomStringUtils.randomAlphanumeric(30);
    }
}
