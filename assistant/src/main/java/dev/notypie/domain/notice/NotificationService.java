package dev.notypie.domain.notice;

import java.io.IOException;

public interface NotificationService {
    void sendTextMessage(Destination destination, String textMessage) throws IOException;
    void sendMessage(Destination destination);
}
