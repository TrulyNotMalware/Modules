package dev.notypie.infrastructure.impl.command.slack;

import dev.notypie.aggregate.commands.entity.Command;
import dev.notypie.aggregate.commands.entity.CommandBuilder;
import dev.notypie.aggregate.commands.entity.EventHandler;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventResponse;
import dev.notypie.service.command.slack.SlackCommandHandler;
import dev.notypie.global.constants.Constants;
import dev.notypie.infrastructure.impl.command.slack.commands.SlackCommand;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventContents;
import dev.notypie.infrastructure.impl.command.slack.dto.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.event.SlackEvent;
import dev.notypie.service.command.slack.SlackRequestParser;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Profile("slack")
@Component
@RequiredArgsConstructor
public class SlackCommandBuilderImpl implements CommandBuilder {

    private final EventHandler<SlackEventContents, SlackEventResponse> responseHandler;
    private final SlackRequestParser slackRequestParser;
    private final SlackCommandHandler slackCommandHandler;

    @Override
    public boolean isSupport(String appType) {
        return appType.equals(Constants.APP_TYPE_SLACK);
    }

    @Override
    public Command buildCommand(@NotNull String appId, Map<String, List<String>> headers,
                                Map<String, Object> payload) {
        SlackEvent<?> event = this.slackRequestParser.parseRequest(headers, payload);
        return Command.NewCommand()
                .appId(appId)
                .commandType(Constants.SLACK_COMMAND_TYPE)
                .commandContext(event.getContext())
                .publisherId(0L)//TEST
                .build();
    }

    @Override
    public ResponseEntity<?> executeCommand(Command command) {
        SlackCommand slackCommand = slackCommandHandler.generateSlackCommand((SlackContext) command.getContext());
        return this.responseHandler.generateEventResponse(slackCommand.generateEventContents());
    }
}
