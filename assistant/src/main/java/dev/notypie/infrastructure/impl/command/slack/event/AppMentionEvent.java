package dev.notypie.infrastructure.impl.command.slack.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.methods.Methods;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import dev.notypie.aggregate.history.entity.History;
import dev.notypie.global.error.ArgumentError;
import dev.notypie.global.error.exceptions.SlackDomainException;
import dev.notypie.global.error.exceptions.SlackErrorCodeImpl;
import dev.notypie.infrastructure.impl.command.slack.dto.*;
import dev.notypie.global.constants.Constants;
import dev.notypie.infrastructure.impl.command.slack.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.contexts.SlackNoticeContext;
import dev.notypie.infrastructure.impl.command.slack.contexts.SlackTextResponseContext;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;

import java.util.*;

import static dev.notypie.global.constants.Constants.ELEMENT_TYPE_TEXT_SECTION;

@Slf4j
@Getter
public class AppMentionEvent extends SlackEvent<SlackContext>{

    private final SlackContext context;
    private final SlackRequestHeaders slackHeaders;

    private final String botId;
    private final String userId;
    private final String rawText;
    private final String channel;
    private final String requestType = Constants.APP_MENTION;
    @Builder
    public AppMentionEvent(String channel, Map<String, List<String>> headers, Map<String, Object> payload, ObjectMapper mapper,
                           String baseUrl, String botToken){
        this.payload = payload;
        this.headers = headers;
        this.slackHeaders = new SlackRequestHeaders(headers);
        this.channel = channel;
        SlackAppMentionDto slackAppMentionDto = mapper.convertValue(payload, SlackAppMentionDto.class);
        AppMentionEventType event = slackAppMentionDto.getEvent();
        this.rawText = event.getRawText();
        this.botId = slackAppMentionDto.getAuthorizations().stream()
                .filter(Authorization::isBot).findFirst()
                .map(Authorization::getUserId)
                .orElse(null);
        this.userId = event.getUserId();
        this.context = this.parseContextFromEvent(event, baseUrl, botToken);
    }

    private SlackContext parseContextFromEvent(AppMentionEventType event, String baseUrl, String botToken){
        return event.getBlocks().stream()
                .filter(block -> !block.getElements().isEmpty()
                        && block.getType().equals(Constants.BLOCK_TYPE_RICH_TEXT))
                .map(block -> block.getElements().getFirst())
                .filter(filterElement -> filterElement.getType().equals(ELEMENT_TYPE_TEXT_SECTION))
                .map(filterElement -> this.extractUserAndCommand(filterElement.getElements()))
                .map(queuePair -> this.buildContext(queuePair.getFirst(), queuePair.getSecond(), baseUrl, botToken))
                .findFirst().orElseThrow();
    }

    private Pair<Queue<String>, Queue<String>> extractUserAndCommand(List<Element> elementList){
        Queue<String> userQueue = new LinkedList<>();
        Queue<String> commandQueue = new LinkedList<>();

        elementList.forEach(element -> {
            switch (element.getType()) {
                case Constants.ELEMENT_TYPE_USER:
                    if(!element.getUserId().equals(this.botId)) {
                        userQueue.offer(element.getUserId());
                    }
                    break;
                case Constants.ELEMENT_TYPE_TEXT:
                    Arrays.stream(element.getText().split(Constants.COMMAND_DELIMITER))
                            .forEach(commandQueue::offer);
                    break;
            }
        });
        this.verifyCommandQueue(commandQueue);
        return Pair.of(userQueue, commandQueue);
    }

    private void verifyCommandQueue(Queue<String> commandQueue) {
        if (commandQueue.isEmpty()) {
            ArgumentError error = new ArgumentError("Command", "null","Command is empty.");
            List<ArgumentError> errors = new ArrayList<>();
            errors.add(error);
            throw new SlackDomainException(SlackErrorCodeImpl.NOT_A_VALID_REQUEST, errors);
        }
    }

    private SlackContext buildContext(Queue<String> userQueue, Queue<String> commandQueue, String baseUrl, String botToken){
        String command = commandQueue.poll().replaceAll(" ","");//remove whitespace
        return switch(command) {
            case Constants.NOTICE_COMMAND -> SlackNoticeContext.newContext()
                    .payload(this.payload).headers(this.headers).requestType(this.requestType)
                    .channel(this.channel).baseUrl(baseUrl).botToken(botToken)
                    .users(userQueue).textQueue(commandQueue)
                    .build();
            default -> SlackTextResponseContext.newContext()
                    .payload(this.payload).headers(this.headers).requestType(this.requestType)
                    .channel(this.channel).baseUrl(baseUrl).botToken(botToken)
                    .responseText("command "+command+" not found")
                    .build();
        };
    }

    @Override
    public SlackContext getContext() {
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
    public SlackRequestHeaders getSlackHeaders() {
        return this.slackHeaders;
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