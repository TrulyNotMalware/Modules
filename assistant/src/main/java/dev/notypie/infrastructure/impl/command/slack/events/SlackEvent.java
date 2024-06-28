package dev.notypie.infrastructure.impl.command.slack.events;


import dev.notypie.aggregate.history.entity.History;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackRequestHeaders;
import dev.notypie.infrastructure.impl.command.slack.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventContents;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Deprecated( forRemoval = true )
public abstract class SlackEvent<Context extends SlackContext> {

    public Map<String, List<String>> headers;
    public Map<String, Object> payload;
    public String userId;

    public abstract Context getContext();
    public abstract String getRequestType();
    public abstract String getRequestBodyAsString();

    public abstract History getEventHistory();

    public SlackRequestHeaders getSlackHeaders(){
        return new SlackRequestHeaders(this.headers);
    }

    public abstract SlackEventContents buildEventContents();
}
