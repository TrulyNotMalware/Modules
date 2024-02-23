package dev.notypie.infrastructure.impl.command.slack.dto.contexts;

import dev.notypie.aggregate.history.entity.History;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackAppMentionDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class SlackAppMentionContext extends SlackContext {

    private final SlackAppMentionDto slackAppMentionDto;

    @Builder
    SlackAppMentionContext(SlackAppMentionDto slackAppMentionDto, String requestType,
                           Map<String, List<String>> headers, Map<String, Object> payload){
        super(headers, payload, requestType);
        this.slackAppMentionDto = slackAppMentionDto;
    }

    @Override
    public History buildEventHistory() {
        return History.builder()

                .build();
    }

    @Override
    public void runCommand() {

    }
}