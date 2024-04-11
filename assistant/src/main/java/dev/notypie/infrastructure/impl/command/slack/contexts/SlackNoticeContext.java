package dev.notypie.infrastructure.impl.command.slack.contexts;

import com.slack.api.methods.Methods;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import dev.notypie.aggregate.history.entity.History;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackChatEventContents;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

@Getter
public class SlackNoticeContext extends SlackContext{

    private final String responseText;
    private final Queue<String> users;

    @Builder(builderMethodName = "newContext")
    SlackNoticeContext(Map<String, List<String>> headers, Map<String, Object> payload, String requestType,
                       String channel, String baseUrl, String botToken, Queue<String> users, Queue<String> textQueue){
        super(headers, payload, requestType, true, channel, baseUrl, botToken);
        this.users = users;
        this.responseText = String.join("", textQueue);
    }

    @Override
    public void executeCommand() {
        SlackChatEventContents chatEventContents = SlackChatEventContents.builder()
                .ok(true)
                .type(Methods.CHAT_POST_MESSAGE)
                .request(ChatPostMessageRequest.builder()
                        .channel(this.channel)
                        .text(this.buildResponseString())
                        .build())
                .build();
        this.broadcastBotResponseToChannel(chatEventContents);
    }

    @Override
    public void validateCommand() {

    }

    @Override
    public History buildEventHistory() {
        return null;
    }

    private String buildResponseString(){
        String userMentions = this.users.stream()
                .map(user -> "<@" + user + ">")
                .collect(Collectors.joining(" "));
        return String.format("[Notice!] %s %s", userMentions, this.responseText);
    }
}
