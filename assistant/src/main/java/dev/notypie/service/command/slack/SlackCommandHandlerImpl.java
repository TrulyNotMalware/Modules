package dev.notypie.service.command.slack;

import dev.notypie.infrastructure.impl.command.slack.commands.AppMentionCommand;
import dev.notypie.infrastructure.impl.command.slack.commands.SlackCommand;
import dev.notypie.infrastructure.impl.command.slack.contexts.SlackContext;
import dev.notypie.infrastructure.impl.command.slack.contexts.SlackAppMentionContext;
import dev.notypie.infrastructure.impl.command.slack.event.SlackEvent;
import dev.notypie.global.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Deprecated(forRemoval = true)
public class SlackCommandHandlerImpl implements SlackCommandHandler {

    @Value("${slack.api.channel}")
    private String channel;

    @Override
    public SlackCommand generateSlackCommand(SlackEvent<? extends SlackContext> event) {
        return switchCase(event.getContext());
    }

    @Override
    public SlackCommand generateSlackCommand(SlackContext context) {
        return switchCase(context);
    }

    private SlackCommand switchCase(SlackContext context){
        // APP_MENTION EVENT command & functions.
        if(context.requestType.equals(Constants.APP_MENTION)){
            SlackCommand slackCommand = AppMentionCommand.builder().context(
                            (SlackAppMentionContext) context)
                    .channel(this.channel).build();
            return slackCommand.getSlackCommand();// get constructed command.
        }
        //FIXME DO NOT RETURN NULL VALUE
        else return null;
    }
}
