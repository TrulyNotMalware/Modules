package dev.notypie.aggregate.orchestrator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReplyHeaders {
    @JsonProperty("commandreply_saga_id")
    private String commandReplySagaId;

    //Class type
    @JsonProperty("commandreply_type")
    private String commandReplyType;

    @JsonProperty("commandreply_reply_to")
    private String commandReplyTo;

    //Class type
    @JsonProperty("commandreply_saga_type")
    private String commandReplySagaType;

    @JsonProperty("commandreply__destination")
    private String commandReplyDestination;

    @JsonProperty("reply_outcome-type")
    private String outcomeType;

    @JsonProperty("reply_type")
    private String replyType;

    @JsonProperty("reply_to_message_id")
    private String replyToMessageId;
}
