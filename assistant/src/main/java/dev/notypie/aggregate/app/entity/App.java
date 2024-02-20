package dev.notypie.aggregate.app.entity;

import java.time.LocalDateTime;

public class App {

    private final String appId;
    private final String appName;
    private final String appType;
    private boolean isAuthenticated;
    private boolean isEnabled;
    private final LocalDateTime registeredDate;

    public App(String appId, String appName, String appType, LocalDateTime createdAt){
        this.appId = appId;
        this.appName = appName;
        this.appType = appType;
        this.isAuthenticated = false;
        this.isEnabled = false;
        this.registeredDate = createdAt;
    }


    public boolean isAvailable(){
        return this.isAuthenticated && this.isEnabled;
    }
}
