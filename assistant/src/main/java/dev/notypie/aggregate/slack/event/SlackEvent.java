package dev.notypie.aggregate.slack.event;


import dev.notypie.aggregate.slack.SlackRequestHeaders;
import dev.notypie.aggregate.slack.dto.Contexts;

public abstract class SlackEvent<Context extends Contexts> {

    public abstract Context getContext();
    public abstract String getRequestType();
    public abstract String getRequestBodyAsString();

    public abstract SlackRequestHeaders getHeaders();


}
