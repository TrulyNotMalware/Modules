package dev.notypie.application;

import dev.notypie.aggregate.slack.event.SlackEvent;

public interface CommandHandler {

    void handleRequest(SlackEvent<?> event);
}
