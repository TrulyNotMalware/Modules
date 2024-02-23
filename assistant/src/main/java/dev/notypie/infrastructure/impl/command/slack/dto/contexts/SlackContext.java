package dev.notypie.infrastructure.impl.command.slack.dto.contexts;

import dev.notypie.aggregate.commands.entity.CommandContext;
import dev.notypie.aggregate.history.entity.History;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackRequestHeaders;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public abstract class SlackContext extends CommandContext {
    /**
     * Reference from Slack Bolt sdk context.
     * The basic slack references
     */
    private boolean tracking = true;
    private final String requestType;
    SlackContext(String requestType){
        super();
        this.requestType = requestType;
    }

    SlackContext(String requestType, boolean tracking){
        super();
        this.requestType = requestType;
        this.tracking = tracking;
    }

    SlackContext(Map<String, List<String>> headers, Map<String, Object> payload, String requestType){
        super(headers, payload);
        this.requestType = requestType;
    }

    SlackContext(Map<String, List<String>> headers, Map<String, Object> payload, String requestType, boolean tracking){
        super(headers, payload);
        this.requestType = requestType;
        this.tracking = tracking;
    }

    public SlackRequestHeaders getSlackHeaders() {
        return new SlackRequestHeaders(super.getHeaders());
    }

    public abstract History buildEventHistory();

}