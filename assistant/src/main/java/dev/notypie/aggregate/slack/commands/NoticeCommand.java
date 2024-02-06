package dev.notypie.aggregate.slack.commands;

import com.slack.api.methods.Methods;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import dev.notypie.aggregate.slack.dto.SlackChatEventContents;
import dev.notypie.aggregate.slack.dto.SlackEventContents;
import dev.notypie.constants.Constants;
import lombok.Builder;
import lombok.Getter;

import java.util.Queue;

@Getter
public class NoticeCommand implements Command{

    private final Command command;
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
        this.command = new ReturnCommand(); // Related Command not exists.
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
