package dev.notypie.aggregate.orchestrator;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Headers {

    @JsonProperty("command_saga_id")
    private String commandSagaId;

    @JsonProperty("PARTITION_ID")
    private String partitionId;

    @JsonProperty("DATE")
    private Date date;

    @JsonProperty("command_type")
    private String commandType;

    @JsonProperty("command_reply_to")
    private String commandReplyTo;

    @JsonProperty("DESTINATION")
    private String destination;

//    @JsonProperty("command")
}