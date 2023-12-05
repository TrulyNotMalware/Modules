package dev.notypie.aggregate.slack.event;


import dev.notypie.aggregate.slack.SlackEventResponse;
import dev.notypie.aggregate.slack.SlackRequestHeaders;
import dev.notypie.aggregate.slack.dto.Contexts;
import dev.notypie.aggregate.slack.dto.SlackEventContents;

public abstract class SlackEvent<Context extends Contexts> {

    public abstract Context getContext();
    public abstract String getRequestType();
    public abstract String getRequestBodyAsString();

    public abstract SlackRequestHeaders getHeaders();

    public abstract SlackEventContents buildEventContents();
}
