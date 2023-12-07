package dev.notypie.application;

import dev.notypie.aggregate.slack.EventHandler;
import dev.notypie.aggregate.slack.SlackEventResponse;
import dev.notypie.aggregate.slack.SlackRequestHeaders;
import dev.notypie.aggregate.slack.SlackRequestParser;
import dev.notypie.aggregate.slack.dto.SlackEventContents;
import dev.notypie.aggregate.slack.event.SlackEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultAssistantServiceImpl implements AssistantService{

    private final EventHandler<SlackEventContents, SlackEventResponse> responseHandler;
    private final SlackRequestParser requestParser;

    public ResponseEntity<SlackEventResponse> categorization(Map<String, List<String>> headers, Map<String, Object> payload){
        SlackEvent<?> slackEvent = requestParser.parseRequest(new SlackRequestHeaders(headers), payload);
        return this.responseHandler.generateEventResponse(slackEvent.buildEventContents());
    }
}