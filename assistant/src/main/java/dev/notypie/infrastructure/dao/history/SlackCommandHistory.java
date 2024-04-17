package dev.notypie.infrastructure.dao.history;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import dev.notypie.infrastructure.impl.command.slack.commands.SlackCommand;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class SlackCommandHistory {

    @Id
    private Long id;

    @Column(name = "app_id")
    @NotNull
    private String appId;

    @Column(name = "team_id")
    @NotNull
    private String teamId;

    @Column(name = "publisher")
    @NotNull
    private String publisher;

    @Column(name = "event_type")
    @NotNull
    private String eventType;

    @Column(name = "channel")
    @NotNull
    private String channel;

    @Column(name = "command", length = 300)
    @NotNull
    private String command;

    //RESERVED_WORDS in Oracle Database
    @Lob
    @Column(name = "raw_data")
    @NotNull
    private String rawData;

    @Column(name = "response_status")
    private int responseStatus;

    @CreatedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    SlackCommandHistory(@NotNull String appId, @NotNull String teamId,
                        @NotNull String eventType, @NotNull String channel,
                        @NotNull String publisher, SlackCommand slackCommand, @NotNull String rawData,
                        int responseStatus){
        this.appId = appId;
        this.teamId = teamId;
        this.eventType = eventType;
        this.channel = channel;
        this.publisher = publisher;
        this.command = slackCommand.toStringCommand();
        this.rawData = rawData;
        this.responseStatus = responseStatus;
    }


}
