package dev.notypie.infrastructure.impl.command.slack.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.aggregate.history.entity.History;
import dev.notypie.infrastructure.impl.command.slack.SlackRequestHeaders;
import dev.notypie.infrastructure.impl.command.slack.dto.UrlVerificationDto;
import dev.notypie.infrastructure.impl.command.slack.dto.contexts.SlackChallengeContext;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventContents;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackUrlVerificationContents;
import dev.notypie.global.constants.Constants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Getter
public class UrlVerificationEvent extends SlackEvent<SlackChallengeContext> {

    private final SlackRequestHeaders requestHeaders;
    private final SlackChallengeContext context;

    private final String requestType = Constants.URL_VERIFICATION;

    public UrlVerificationEvent(Map<String, List<String>> headers, Map<String, Object> payload, ObjectMapper mapper){
        this.requestHeaders = new SlackRequestHeaders(headers);
        this.context = SlackChallengeContext.builder()
                .urlVerificationDto(mapper.convertValue(payload, UrlVerificationDto.class))
                .requestType(this.requestType)
                .headers(headers)
                .payload(payload)
                .build();

    }

    @Override
    public SlackChallengeContext getContext() {
        return this.context;
    }

    @Override
    public String getRequestType() {return this.requestType;}

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
        return this.requestHeaders;
    }

    @Override
    public SlackEventContents buildEventContents() {
        return SlackUrlVerificationContents.builder()
                .challenge(this.context.getUrlVerificationDto().getChallenge())
                .ok(true)
                .build();
    }

}
