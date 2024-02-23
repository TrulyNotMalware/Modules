package dev.notypie.infrastructure.dao.app.jpa;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import dev.notypie.aggregate.app.entity.App;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisteredApp {

    @Id
    private String appId;

    @Column(name = "app_name")
    private String appName;

    @Column(name = "app_type")
    private String appType;

    @Column(name = "is_authenticated")
    private boolean isAuthenticated;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @CreatedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Builder(builderMethodName = "toTable")
    RegisteredApp(App app){
        this.appId = app.getAppId();
        this.appName = app.getAppName();
        this.appType = app.getAppType();
        this.isAuthenticated = app.isAuthenticated();
        this.isEnabled = app.isEnabled();
    }

    App toDomainEntity(){
        return App.toEntity()
                .appId(this.appId)
                .appName(this.appName)
                .appType(this.appType)
                .isAuthenticated(this.isAuthenticated)
                .isEnabled(this.isEnabled)
                .registeredDate(this.createdAt)
                .build();
    }
}
