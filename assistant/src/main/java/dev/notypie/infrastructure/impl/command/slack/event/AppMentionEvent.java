package dev.notypie.infrastructure.impl.command.slack.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.methods.Methods;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import dev.notypie.aggregate.history.entity.History;
import dev.notypie.infrastructure.impl.command.slack.SlackRequestHeaders;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackAppMentionDto;
import dev.notypie.infrastructure.impl.command.slack.dto.contexts.SlackAppMentionContext;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventContents;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackChatEventContents;
import dev.notypie.global.constants.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Getter
public class AppMentionEvent extends SlackEvent<SlackAppMentionContext>{

    private final SlackAppMentionContext context;
    private final SlackRequestHeaders headers;

    private final String channel;
    private final String requestType = Constants.APP_MENTION;

    @Builder
    public AppMentionEvent(String channel, Map<String, List<String>> headers, Map<String, Object> payload, ObjectMapper mapper){
        this.headers = new SlackRequestHeaders(headers);
        this.context = SlackAppMentionContext.builder()
                .slackAppMentionDto(mapper.convertValue(payload, SlackAppMentionDto.class))
                .requestType(this.requestType)
                .headers(headers)
                .payload(payload)
                .build();
        this.channel = channel;
    }

    @Override
    public SlackAppMentionContext getContext() {
        return this.context;
    }

    @Override
    public String getRequestType() {
        return this.requestType;
    }

    @Override
    public String getRequestBodyAsString() {
        return null;
    }

    @Override
    public History getEventHistory() {
        return this.context.buildEventHistory();
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
