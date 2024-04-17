package dev.notypie.infrastructure.dao.app.jpa.schema;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import dev.notypie.aggregate.app.entity.App;
import dev.notypie.command.app.AppAuthorizeCommand;
import dev.notypie.command.app.AppEnableCommand;
import dev.notypie.command.app.AppRegisterCommand;
import dev.notypie.event.app.AppAuthorizeEvent;
import dev.notypie.event.app.AppRegisteredCompletedEvent;
import dev.notypie.event.app.AppRegisteredEvent;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Entity
@Getter
@Aggregate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisteredApp { //State Store Aggregate

    @AggregateIdentifier
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

    @CommandHandler
    protected RegisteredApp(AppRegisterCommand registerCommand){
        log.info("handling {}", registerCommand);
        this.appId = registerCommand.getAppId();
        this.appName = registerCommand.getAppName();
        this.appType = registerCommand.getAppType();
        this.isAuthenticated = false;
        this.isEnabled = false;
        this.createdAt = registerCommand.getRegisteredDate();

        apply(AppRegisteredEvent.builder()
                .appId(this.appId).appName(this.appName)
                .build());
    }

    @CommandHandler
    protected void getEnableApp(AppAuthorizeCommand appAuthorizeCommand){
        log.info("handling {}", appAuthorizeCommand);
        apply(AppAuthorizeEvent.builder().transactionId(UUID.randomUUID().toString())
                .appId(appAuthorizeCommand.getAppId()).ownerId(appAuthorizeCommand.getCreatorId()).build());
    }

    @CommandHandler
    protected void enableApp(AppEnableCommand enableCommand){
        log.info("handling {}", enableCommand);
        this.isEnabled = true;
        apply(AppRegisteredCompletedEvent.builder()
                .appId(this.appId).authenticatedDate(LocalDateTime.now())
                .build());
    }

    @Builder(builderMethodName = "toTable")
    RegisteredApp(App app){
        this.appId = app.getAppId();
        this.appName = app.getAppName();
        this.appType = app.getAppType();
        this.isAuthenticated = app.isAuthenticated();
        this.isEnabled = app.isEnabled();
    }

    public App toDomainEntity(){
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
