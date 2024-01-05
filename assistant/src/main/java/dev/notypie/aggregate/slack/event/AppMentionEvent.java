package dev.notypie.aggregate.slack.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.methods.Methods;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import dev.notypie.aggregate.slack.SlackRequestHeaders;
import dev.notypie.aggregate.slack.dto.SlackAppMentionContext;
import dev.notypie.aggregate.slack.dto.SlackEventContents;
import dev.notypie.aggregate.slack.dto.SlackChatEventContents;
import dev.notypie.constants.Constants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@Slf4j
@Getter
public class AppMentionEvent extends SlackEvent<SlackAppMentionContext>{

    private final SlackAppMentionContext context;
    private final SlackRequestHeaders headers;

    private String channel;

    public AppMentionEvent(String channel, SlackRequestHeaders headers, Map<String, Object> payload, ObjectMapper mapper){
        this.headers = headers;
        this.context = mapper.convertValue(payload, SlackAppMentionContext.class);
        this.channel = channel;
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
                .type(Methods.CHAT_POST_MESSAGE)
                .request(ChatPostMessageRequest.builder()
                        .channel(this.channel)
                        .text("This is test mention")
                        .build())
                .build();
    }
}
