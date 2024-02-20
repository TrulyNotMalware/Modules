package dev.notypie.infrastructure.impl.command.slack.event;


import dev.notypie.aggregate.history.entity.History;
import dev.notypie.infrastructure.impl.command.slack.SlackRequestHeaders;
import dev.notypie.infrastructure.impl.command.slack.dto.contexts.Contexts;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventContents;

public abstract class SlackEvent<Context extends Contexts> {

    public abstract Context getContext();
    public abstract String getRequestType();
    public abstract String getRequestBodyAsString();

    public abstract History getEventHistory();

    public abstract SlackRequestHeaders getHeaders();

    public abstract SlackEventContents buildEventContents();
}
