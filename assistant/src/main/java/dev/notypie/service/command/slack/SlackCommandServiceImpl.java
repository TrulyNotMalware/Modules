package dev.notypie.service.command.slack;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.aggregate.app.entity.App;
import dev.notypie.aggregate.app.repository.AppRepository;
import dev.notypie.aggregate.commands.entity.Command;
import dev.notypie.aggregate.commands.entity.PayloadKeyNames;
import dev.notypie.global.constants.Constants;
import dev.notypie.global.error.ArgumentError;
import dev.notypie.global.error.exceptions.CommandErrorCodeImpl;
import dev.notypie.global.error.exceptions.CommandException;
import dev.notypie.global.error.exceptions.SlackDomainException;
import dev.notypie.global.error.exceptions.SlackErrorCodeImpl;
import dev.notypie.infrastructure.impl.command.slack.commands.ExecuteSlackCommand;
import dev.notypie.infrastructure.impl.command.slack.dto.*;
import dev.notypie.infrastructure.impl.command.slack.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.event.AppMentionEvent;
import dev.notypie.infrastructure.impl.command.slack.event.SlackEvent;
import dev.notypie.infrastructure.impl.command.slack.event.UrlVerificationEvent;
import dev.notypie.service.command.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@Qualifier(Constants.SLACK_COMMAND_SERVICE)
@RequiredArgsConstructor
public class SlackCommandServiceImpl implements CommandService {

    private final ObjectMapper objectMapper;
    private final AppRepository appRepository;
    private final CommandGateway commandGateway;

    @Value("${slack.api.channel}")
    private String channel;

    @Value("${core.requester.config.baseUrl:}")
    private String baseUrl;

    @Value("${slack.api.token}")
    private String botToken;

//    @Override
//    public ResponseEntity<?> executeCommand(Map<String, List<String>> headers, Map<String, Object> payload) {
//        Command command = this.buildCommand(headers, payload);
//        return this.executeCommand(command);
//    }

    @Override
    public ResponseEntity<?> executeCommand(Map<String, List<String>> headers, Map<String, Object> payload) {
        String appId = this.resolveAppId(payload);
        SlackEvent<SlackContext> event = this.parseSlackEventFromRequest(headers, payload);
        this.commandGateway.send(ExecuteSlackCommand.builder().appId(appId).event(event).build());
        return null;
    }

    @Override
    public Command buildCommand(Map<String, List<String>> headers, Map<String, Object> payload) {
        String appId = resolveAppId(payload);
        log.info("appId = "+appId);
        App app = this.appRepository.findByAppId(appId);

        SlackEvent<SlackContext> event = this.parseSlackEventFromRequest(headers, payload);
        return Command.NewCommand()
                .appId(app.getAppId())
                .commandType(Constants.SLACK_COMMAND_TYPE)
                .commandContext(event.getContext())
                .publisherId(0L)//TEST
                .build();
    }

    @Override
    public ResponseEntity<?> executeCommand(Command command) {
        log.info("execute command"+command.getContext());
        command.getContext().executeCommand();
        return null;
    }


    private String resolveAppId(Map<String, Object> payload){
        return Arrays.stream(PayloadKeyNames.values())
                .filter(key -> payload.containsKey(key.getKeyName()))
                .findFirst()
                .map(key -> payload.get(key.getKeyName()).toString())
                .orElseThrow(() ->
                        new CommandException(CommandErrorCodeImpl.COMMAND_TYPE_NOT_DETECTED));
    }

    private SlackEvent<SlackContext> parseSlackEventFromRequest(Map<String, List<String>> headers, Map<String, Object> payload)  {
        if(!payload.containsKey("type")){
            List<ArgumentError> argumentErrors = new ArrayList<>();
            argumentErrors.add(new ArgumentError("type","null","type cannot be NULL"));
            throw new SlackDomainException(SlackErrorCodeImpl.NOT_A_VALID_REQUEST, argumentErrors);
        }
        String payloadType = payload.get("type").toString();
        return switch (payloadType) {
            case Constants.URL_VERIFICATION -> new UrlVerificationEvent(headers, payload, this.objectMapper);
            case Constants.EVENT_CALLBACK -> this.handleEventCallbackRequest(
                    this.objectMapper.convertValue(payload.get("event"), AppMentionEventType.class),
                    headers, payload);
            default -> {
                log.info("Except Unsupported events. type is {}", payloadType);
                throw new SlackDomainException(SlackErrorCodeImpl.EVENT_NOT_SUPPORTED, null);
            }
        };
    }

    private SlackEvent<SlackContext> handleEventCallbackRequest(AppMentionEventType event, Map<String, List<String>> headers, Map<String, Object> payload){
        String payloadType = payload.get("type").toString();
        return switch (event.getType()) {
            case Constants.APP_MENTION -> new AppMentionEvent(this.channel, headers, payload, this.objectMapper, this.baseUrl, this.botToken);
            case Constants.MESSAGE_EVENT -> {
                //FIXME Update later.
                log.info("Except Unsupported events. type is {}", payloadType);
                throw new SlackDomainException(SlackErrorCodeImpl.EVENT_NOT_SUPPORTED, null);
            }
            default -> {
                log.info("Except Unsupported events. type is {}", payloadType);
                throw new SlackDomainException(SlackErrorCodeImpl.EVENT_NOT_SUPPORTED, null);
            }
        };
    }
}