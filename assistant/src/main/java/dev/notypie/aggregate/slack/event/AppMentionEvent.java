package dev.notypie.aggregate.slack.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.aggregate.slack.SlackRequestHeaders;
import dev.notypie.aggregate.slack.dto.SlackAppMentionContext;
import dev.notypie.aggregate.slack.dto.SlackEventContents;
import dev.notypie.aggregate.slack.dto.SlackChatEventContents;
import dev.notypie.constants.Constants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Getter
public class AppMentionEvent extends SlackEvent<SlackAppMentionContext>{

    private final SlackAppMentionContext context;
    private final SlackRequestHeaders headers;

    public AppMentionEvent(SlackRequestHeaders headers, Map<String, Object> payload, ObjectMapper mapper){
        this.headers = headers;
        this.context = mapper.convertValue(payload, SlackAppMentionContext.class);
    }

    @Override
    public SlackAppMentionContext getContext() {
        return this.context;
    }

    @Override
    public String getRequestType() {
        return Constants.APP_MENTION;
    }

    @Override
    public String getRequestBodyAsString() {
        return null;
    }

    @Override
    public SlackRequestHeaders getHeaders() {
        return this.headers;
    }

    @Override
    public SlackEventContents buildEventContents() {
        return SlackChatEventContents.builder()
                .ok(true)
                .build();
    }
}
