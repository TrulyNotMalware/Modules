package dev.notypie.aggregate.slack.dto;

import dev.notypie.aggregate.slack.EventHistory;

import java.util.List;

public abstract class Contexts {
    /**
     * Reference from Slack Bolt sdk context.
     * The basic slack references
     */
    protected boolean tracking = true;
    protected String teamId;
    protected String botToken;
    protected List<String> botScopes;
    protected String botId;
    protected String botUserId;
    protected String requestUserId;
    protected String requestUserToken;
    protected List<String> requestUserScopes;

    protected abstract EventHistory getHistoryContents();
}