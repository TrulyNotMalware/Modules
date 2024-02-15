package dev.notypie.aggregate.slack.dto.contexts;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.notypie.aggregate.history.domain.EventHistory;

import java.util.List;

public abstract class Contexts {
    /**
     * Reference from Slack Bolt sdk context.
     * The basic slack references
     */
    protected boolean tracking = true;

    @JsonProperty("team_id")
    protected String teamId;

    @JsonProperty("token")
    protected String token;
    protected List<String> botScopes;
    protected String botId;
    protected String botUserId;
    protected String requestUserId;
    protected String requestUserToken;
    protected List<String> requestUserScopes;

    public abstract EventHistory buildEventHistory();
}