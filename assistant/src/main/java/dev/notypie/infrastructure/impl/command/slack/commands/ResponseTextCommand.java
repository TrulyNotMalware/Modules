package dev.notypie.infrastructure.impl.command.slack.commands;

import com.slack.api.methods.Methods;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackChatEventContents;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventContents;
import lombok.Builder;
import lombok.Getter;

@Getter
@Deprecated(forRemoval = true)
public class ResponseTextCommand implements SlackCommand {

    private final SlackCommand slackCommand;
    private final String body;
    private final String channel;

    @Builder
    ResponseTextCommand(String channel, String body){
        this.slackCommand = new ReturnCommand();
        this.channel = channel;
        this.body = body;
    }

    @Override
    public String toStringCommand() {
        return this.slackCommand.toStringCommand();
    }

    @Override
    public SlackEventContents generateEventContents() {
        return SlackChatEventContents.builder()
                .ok(true)
                .type(Methods.CHAT_POST_MESSAGE)
                .request(ChatPostMessageRequest.builder()
                        .channel(this.channel)
                        .text(this.body)
                        .build())
                .build();
    }
}
