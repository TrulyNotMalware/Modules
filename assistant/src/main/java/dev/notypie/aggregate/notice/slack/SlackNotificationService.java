package dev.notypie.aggregate.notice.slack;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import dev.notypie.aggregate.notice.Destination;
import dev.notypie.aggregate.notice.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Profile("slack")
@Service
public class SlackNotificationService implements NotificationService {

    @Value("${slack.api.token}")
    private String token;

    @Value("${slack.api.channel}")
    private String channel;

    @Override
    public void sendTextMessage(Destination destination, String textMessage) throws SlackApiException, IOException {
        MethodsClient api = Slack.getInstance().methods(this.token);
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(this.channel)
                .text(textMessage)
                .build();
        api.chatPostMessage(request);
    }

    @Override
    public void sendMessage(Destination destination) {
        //TODO NOT IMPLEMENTED YET
    }
}