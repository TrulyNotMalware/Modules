package dev.notypie.infrastructure.impl.command.slack.dto.contexts;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.notypie.aggregate.history.entity.History;

import java.util.List;

public abstract class Contexts {
    /**
     * Reference from Slack Bolt sdk context.
     * The basic slack references
     */
    public boolean tracking = true;

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

    public abstract History buildEventHistory();
}