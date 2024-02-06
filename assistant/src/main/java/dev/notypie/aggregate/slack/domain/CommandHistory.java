package dev.notypie.aggregate.slack.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import dev.notypie.aggregate.slack.commands.Command;
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
public class CommandHistory {

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

    @Column(name = "data", columnDefinition = "TEXT")
    @NotNull
    private String data;

    @Column(name = "response_status")
    private int responseStatus;

    @CreatedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    CommandHistory(@NotNull String appId, @NotNull String teamId,
                   @NotNull String eventType, @NotNull String channel,
                   @NotNull String publisher, Command command, @NotNull String data,
                   int responseStatus){
        this.appId = appId;
        this.teamId = teamId;
        this.eventType = eventType;
        this.channel = channel;
        this.publisher = publisher;
        this.command = command.toStringCommand();
        this.data = data;
        this.responseStatus = responseStatus;
    }


}
