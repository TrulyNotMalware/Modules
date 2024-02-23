package dev.notypie.infrastructure.dao.jpa;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublishHeaders {
    @JsonProperty("command_saga_id")
    private String commandSagaId;

    //Class type
    @JsonProperty("command_type")
    private String commandType;

    @JsonProperty("command_reply_to")
    private String commandReplyTo;

    //Class type
    @JsonProperty("command_saga_type")
    private String commandSagaType;

    @JsonProperty("command__destination")
    private String commandDestination;
}
