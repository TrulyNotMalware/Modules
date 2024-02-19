package dev.notypie.aggregate.slack.commands;

import com.slack.api.methods.Methods;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import dev.notypie.aggregate.slack.dto.SlackChatEventContents;
import dev.notypie.aggregate.slack.dto.SlackEventContents;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseTextCommand implements Command{

    private final Command command;
    private final String body;
    private final String channel;

    @Builder
    ResponseTextCommand(String channel, String body){
        this.command = new ReturnCommand();
        this.channel = channel;
        this.body = body;
    }

    @Override
    public String toStringCommand() {
        return this.command.toStringCommand();
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
