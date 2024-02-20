package dev.notypie.infrastructure.impl.command.slack.commands;

import com.slack.api.methods.Methods;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackChatEventContents;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventContents;
import dev.notypie.global.constants.Constants;
import lombok.Builder;
import lombok.Getter;

import java.util.Queue;

@Getter
public class NoticeCommand implements SlackCommand {

    private final SlackCommand slackCommand;
    private final String channel;
    private final String text;
    private final Queue<String> users;
    @Builder
    public NoticeCommand(String channel, Queue<String> users, Queue<String> textQueue){
        this.channel = channel;
        StringBuilder builder = new StringBuilder();
        for(String text : textQueue) builder.append(text);
        this.text = builder.toString();
        this.users = users;
        this.slackCommand = new ReturnCommand(); // Related Command not exists.
    }

    @Override
    public String toStringCommand() {
        return Constants.NOTICE_COMMAND;
    }

    @Override
    public SlackEventContents generateEventContents() {
        return SlackChatEventContents.builder()
                .ok(true)
                .type(Methods.CHAT_POST_MESSAGE)
                .request(ChatPostMessageRequest.builder()
                        .channel(this.channel)
                        .text(this.buildResponseString())
                        .build())
                .build();
    }

    private String buildResponseString(){
        StringBuilder builder = new StringBuilder();
        builder.append("[Notice!] ");
        for( String user : this.users ){
            builder.append("<@").append(user).append("> ");
        }
        builder.append(this.text);
        return builder.toString();
    }
}
