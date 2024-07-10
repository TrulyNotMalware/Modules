package dev.notypie.domain.notice;

import com.slack.api.methods.SlackApiException;

import java.io.IOException;

public interface NotificationService {
    void sendTextMessage(Destination destination, String textMessage) throws SlackApiException, IOException;
    void sendMessage(Destination destination);
}
