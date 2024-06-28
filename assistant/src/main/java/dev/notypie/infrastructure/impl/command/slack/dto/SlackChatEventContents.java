package dev.notypie.infrastructure.impl.command.slack.dto;

import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Deprecated( forRemoval = true )
public class SlackChatEventContents extends SlackEventContents{

    private ChatPostMessageRequest request;
}