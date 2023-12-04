package dev.notypie.aggregate.slack.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.aggregate.slack.SlackRequestHeaders;
import dev.notypie.aggregate.slack.dto.SlackChallengeContext;
import dev.notypie.constants.Constants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Getter
public class UrlVerificationEvent extends SlackEvent<SlackChallengeContext> {

    private final SlackRequestHeaders requestHeaders;
    private final SlackChallengeContext context;

    public UrlVerificationEvent(SlackRequestHeaders headers, Map<String, Object> payload, ObjectMapper mapper){
        this.requestHeaders = headers;
        this.context = mapper.convertValue(payload, SlackChallengeContext.class);
    }

    @Override
    public SlackChallengeContext getContext() {
        return this.context;
    }

    @Override
    public String getRequestType() {return Constants.URL_VERIFICATION;}

    @Override
    public String getRequestBodyAsString() {
        return null;
    }

    @Override
    public SlackRequestHeaders getHeaders() {
        return this.requestHeaders;
    }

}
