package dev.notypie.aggregate.orchestrator;

import jakarta.persistence.*;

import java.util.Map;

@Entity
public class Message {
    @Id
    @Column(name = "id") //UUID value
    private String id;

    @Column(name = "destination")
    private String destination;

}