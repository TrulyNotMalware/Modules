package dev.notypie.infrastructure.application;

import dev.notypie.infrastructure.impl.command.slack.commands.AppMentionCommand;
import dev.notypie.infrastructure.impl.command.slack.commands.SlackCommand;
import dev.notypie.infrastructure.impl.command.slack.dto.contexts.Contexts;
import dev.notypie.infrastructure.impl.command.slack.dto.contexts.SlackAppMentionContext;
import dev.notypie.infrastructure.impl.command.slack.event.SlackEvent;
import dev.notypie.global.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SlackCommandHandlerImpl implements SlackCommandHandler {

    @Value("${slack.api.channel}")
    private String channel;

    @Override
    public SlackCommand handleRequest(SlackEvent<? extends Contexts> event) {
        // APP_MENTION EVENT command & functions.
        if(event.getRequestType().equals(Constants.APP_MENTION)){
            SlackCommand slackCommand = AppMentionCommand.builder().context(
                    (SlackAppMentionContext) event.getContext())
                    .channel(this.channel).build();
            return slackCommand.getSlackCommand();// get constructed command.
        }
        //FIXME DO NOT RETURN NULL VALUE
        return null;
    }


}
