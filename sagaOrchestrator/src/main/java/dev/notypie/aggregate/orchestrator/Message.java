package dev.notypie.aggregate.orchestrator;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "message")
public class Message {
    @Id
    @Column(name = "id") //UUID value
    private String id;

    @Column(name = "destination", length = 300)
    private String destination;

    //Json String converter
    @Column(name = "headers", length = 1000)
    @Convert(converter = Headers.HeaderConverter.class)
    private Headers headers;

    @Column(name = "payload", length = 300)
    private String payload;

    @Column(name = "published")//smallint
    private short published;

    @Column(name = "creation_time")
    private long creationTime;

    @Column(name = "message_partition")
    private long messagePartition;
}