package dev.notypie.aggregate.slack.dto;

import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SlackChatEventContents extends SlackEventContents{

    private ChatPostMessageRequest request;
}