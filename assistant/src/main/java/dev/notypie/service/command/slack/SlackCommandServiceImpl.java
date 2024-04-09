package dev.notypie.service.command.slack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.api.methods.Methods;
import dev.notypie.aggregate.app.entity.App;
import dev.notypie.aggregate.app.repository.AppRepository;
import dev.notypie.aggregate.commands.entity.Command;
import dev.notypie.aggregate.commands.entity.EventHandler;
import dev.notypie.aggregate.commands.entity.PayloadKeyNames;
import dev.notypie.global.constants.Constants;
import dev.notypie.global.error.ArgumentError;
import dev.notypie.global.error.exceptions.CommandErrorCodeImpl;
import dev.notypie.global.error.exceptions.CommandException;
import dev.notypie.global.error.exceptions.SlackDomainException;
import dev.notypie.global.error.exceptions.SlackErrorCodeImpl;
import dev.notypie.infrastructure.impl.command.slack.commands.AppMentionCommand;
import dev.notypie.infrastructure.impl.command.slack.commands.SlackCommand;
import dev.notypie.infrastructure.impl.command.slack.dto.*;
import dev.notypie.infrastructure.impl.command.slack.dto.contexts.SlackAppMentionContext;
import dev.notypie.infrastructure.impl.command.slack.dto.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.event.AppMentionEvent;
import dev.notypie.infrastructure.impl.command.slack.event.SlackEvent;
import dev.notypie.infrastructure.impl.command.slack.event.UrlVerificationEvent;
import dev.notypie.service.command.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static dev.notypie.requester.RestClientRequester.defaultContentType;

@Slf4j
@Service
@Qualifier(Constants.SLACK_COMMAND_SERVICE)
@RequiredArgsConstructor
public class SlackCommandServiceImpl implements CommandService {

    private final ObjectMapper objectMapper;
    private final AppRepository appRepository;

    @Value("${slack.api.channel}")
    private String channel;

    @Value("${core.requester.config.baseUrl:}")
    private String baseUrl;

    @Value("${slack.api.token}")
    private String botToken;

    //FIXME Remove this Component & refactoring Domain logic.
    private final SlackCommandHandler slackCommandHandler;
    private final EventHandler<SlackEventContents, SlackEventResponse> responseHandler;

    @Override
    public Command buildCommand(Map<String, List<String>> headers, Map<String, Object> payload) {
        String appId = resolveAppId(payload);
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
//        command
        SlackCommand slackCommand = slackCommandHandler.generateSlackCommand((SlackContext) command.getContext());
        return this.responseHandler.generateEventResponse(slackCommand.generateEventContents());
//        new CommandException(CommandErrorCodeImpl.UNKNOWN_COMMAND_TYPE);
    }

    private String resolveAppId(Map<String, Object> payload){
        return Arrays.stream(PayloadKeyNames.values())
                .filter(key -> payload.containsKey(key.getKeyName()))
                .findFirst()
                .map(key -> payload.get(key.getKeyName()).toString())
                .orElseThrow(() ->
                        new CommandException(CommandErrorCodeImpl.COMMAND_TYPE_NOT_DETECTED));
    }

    private SlackCommand switchCase(SlackContext context){
        // APP_MENTION EVENT command & functions.
        if(context.getRequestType().equals(Constants.APP_MENTION)){
            SlackCommand slackCommand = AppMentionCommand.builder().context(
                            (SlackAppMentionContext) context)
                    .channel(this.channel).build();
            return slackCommand.getSlackCommand();// get constructed command.
        }
        //FIXME DO NOT RETURN NULL VALUE
        else return null;
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
            case Constants.EVENT_CALLBACK -> this.handleEventCallbackCommand(
                    this.objectMapper.convertValue(payload.get("event"), AppMentionEventType.class),
                    headers, payload);
            default -> {
                log.info("Except Unsupported events. type is {}", payloadType);
                throw new SlackDomainException(SlackErrorCodeImpl.EVENT_NOT_SUPPORTED, null);
            }
        };
    }

    private SlackEvent<SlackContext> handleEventCallbackCommand(AppMentionEventType event, Map<String, List<String>> headers, Map<String, Object> payload){
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

//    public ResponseEntity<SlackEventResponse> generateEventResponse(SlackEventContents event) {
//        if(event.getType().equals(Methods.CHAT_POST_MESSAGE)){
//            log.info("Chat Post requests");
//            SlackChatEventContents chatEvent = (SlackChatEventContents) event;
//            ResponseEntity<SlackApiResponse> response = this.requester.post(Methods.CHAT_POST_MESSAGE, this.botToken, chatEvent.getRequest(), SlackApiResponse.class);
//            log.info("response:"+response.getBody());
//            if( !Objects.requireNonNull(response.getBody()).isOk() )
//                throw new SlackDomainException(SlackErrorCodeImpl.NOT_A_VALID_REQUEST, null);
//        }
//        return new ResponseEntity<>(SlackEventResponse.builder()
//                .contentType(defaultContentType)
//                .eventContents(event)
//                .build(), HttpStatus.OK);
//    }
}
