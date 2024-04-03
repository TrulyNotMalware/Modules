package dev.notypie.service.command.slack;

import dev.notypie.aggregate.app.entity.App;
import dev.notypie.aggregate.app.repository.AppRepository;
import dev.notypie.aggregate.commands.entity.Command;
import dev.notypie.aggregate.commands.entity.EventHandler;
import dev.notypie.aggregate.commands.entity.PayloadKeyNames;
import dev.notypie.global.constants.Constants;
import dev.notypie.global.error.exceptions.CommandErrorCodeImpl;
import dev.notypie.global.error.exceptions.CommandException;
import dev.notypie.infrastructure.impl.command.slack.commands.SlackCommand;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventContents;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventResponse;
import dev.notypie.infrastructure.impl.command.slack.dto.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.event.SlackEvent;
import dev.notypie.service.command.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SlackServiceImpl implements CommandService {

    private final AppRepository appRepository;
    private final SlackRequestParser slackRequestParser;
    private final EventHandler<SlackEventContents, SlackEventResponse> responseHandler;
    //FIXME Remove this Component & refactoring Domain logic.
    private final SlackCommandHandler slackCommandHandler;

    @Override
    public Command buildCommand(Map<String, List<String>> headers, Map<String, Object> payload) {
        String appId = resolveAppId(payload);
        App app = this.appRepository.findByAppId(appId);

        SlackEvent<?> event = this.slackRequestParser.parseRequest(headers, payload);
        return Command.NewCommand()
                .appId(app.getAppId())
                .commandType(Constants.SLACK_COMMAND_TYPE)
                .commandContext(event.getContext())
                .publisherId(0L)//TEST
                .build();
    }

    @Override
    public ResponseEntity<?> executeCommand(Command command) {
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
}
