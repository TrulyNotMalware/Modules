package dev.notypie.infrastructure.impl.command.slack.contexts;

import dev.notypie.aggregate.history.entity.History;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class SlackTextResponseContext extends SlackContext{

    private final String responseText;
    @Builder(builderMethodName = "newContext")
    public SlackTextResponseContext(Map<String, List<String>> headers, Map<String, Object> payload, String requestType,
                       String channel, String baseUrl, String botToken, String responseText){
        super(headers, payload, requestType, true, channel, baseUrl, botToken);
        this.responseText = responseText;
    }
    @Override
    public void executeCommand() {
        this.broadcastBotResponseToChannel(this.responseText);
    }

    @Override
    public void validateCommand() {

    }

    @Override
    public History buildEventHistory() {
        return null;
    }
}
