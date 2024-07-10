package dev.notypie.domain.history.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * History domain Root Entity
 * history must not be changed
 */
public class History {

    private final Long historyId;
    private final Long commandId;
    // Related with User domain
    private final Long publisherId;
    private final String eventType;
    private final String rawText;
    private final boolean isBotRequest;
    private final ApplicationInfo applicationInfo;
    private final LocalDateTime createdAt;

    @Builder
    public History(
            @NotNull Long historyId, @NotNull Long commandId, @NotNull Long publisherId, @NotNull String eventType,
            @NotNull String rawText, boolean isBotRequest,@NotNull String appId, @NotNull String appName,
            @NotNull String teamName, @NotNull String source, boolean isEnterprise, LocalDateTime createdAt
    ){
        this.historyId = historyId;
        this.commandId = commandId;
        this.publisherId = publisherId;
        this.eventType = eventType;
        this.rawText = rawText;
        this.isBotRequest = isBotRequest;
        this.applicationInfo = ApplicationInfo.builder()
                .appId(appId).appName(appName).teamName(teamName)
                .source(source).isEnterprise(isEnterprise)
                .build();
        this.createdAt = createdAt;
    }
}
