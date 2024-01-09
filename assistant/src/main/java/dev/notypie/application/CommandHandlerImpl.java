package dev.notypie.application;

import dev.notypie.aggregate.slack.commands.AppMentionCommand;
import dev.notypie.aggregate.slack.commands.Command;
import dev.notypie.aggregate.slack.dto.SlackAppMentionContext;
import dev.notypie.aggregate.slack.event.SlackEvent;
import dev.notypie.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommandHandlerImpl implements CommandHandler{

    @Override
    public void handleRequest(SlackEvent<?> event) {
        // APP_MENTION EVENT command & functions.
        if(event.getRequestType().equals(Constants.APP_MENTION)){
            Command command = AppMentionCommand.builder().context(
                    (SlackAppMentionContext) event.getContext()).build();
            
        }
    }


}
