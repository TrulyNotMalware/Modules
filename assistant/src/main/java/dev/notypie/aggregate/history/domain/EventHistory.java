package dev.notypie.aggregate.history.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventHistory {

    @Id
    private String eventId;

    @Column(name = "request_app_name")
    @NotNull
    private String requestAppName;

    @Column(name = "request_app_id")
    @NotNull
    private String requestAppId;

    @Column(name = "publisher")
    @NotNull
    private String publisher;

    @Column(name = "event_type")
    @NotNull
    private String eventType;

    @Column(name = "source")
    @NotNull
    private String source;

    @Column(name = "raw_data", columnDefinition = "TEXT")
    private String rawData;

    @Column(name = "expected_command")
    private String command;

    @CreatedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    EventHistory(@NotNull String eventId, @NotNull String requestAppName, @NotNull String requestAppId,
                 @NotNull String publisher, @NotNull String eventType,
                 @NotNull String source, @NotNull String rawData, @NotNull String command
    ){
        this.eventId = eventId;
        this.requestAppName = requestAppName;
        this.requestAppId = requestAppId;
        this.publisher = publisher;
        this.eventType = eventType;
        this.source = source;
        this.rawData = rawData;
        this.command = command;
    }
}
