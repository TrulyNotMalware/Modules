package dev.notypie.infrastructure.dao.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "received_messages")
public class ReceivedMessages {
    @Id
    @Column(name = "consumer_id", length = 300)
    private String consumerId;

    @Column(name = "message_id", length = 300)
    private String messageId;

    @Column(name = "creation_time")
    private long creationTime;
}
