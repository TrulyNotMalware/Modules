package dev.notypie.application;

import dev.notypie.aggregate.slack.commands.AppMentionCommand;
import dev.notypie.aggregate.slack.commands.Command;
import dev.notypie.aggregate.slack.dto.contexts.Contexts;
import dev.notypie.aggregate.slack.dto.contexts.SlackAppMentionContext;
import dev.notypie.aggregate.slack.event.SlackEvent;
import dev.notypie.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommandHandlerImpl implements CommandHandler{

    @Value("${slack.api.channel}")
    private String channel;

    @Override
    public Command handleRequest(SlackEvent<? extends Contexts> event) {
        // APP_MENTION EVENT command & functions.
        if(event.getRequestType().equals(Constants.APP_MENTION)){
            Command command = AppMentionCommand.builder().context(
                    (SlackAppMentionContext) event.getContext())
                    .channel(this.channel).build();
            return command.getCommand();// get constructed command.
        }
        //FIXME DO NOT RETURN NULL VALUE
        return null;
    }


}
