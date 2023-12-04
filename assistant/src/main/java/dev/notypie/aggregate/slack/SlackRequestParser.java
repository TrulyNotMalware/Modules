package dev.notypie.aggregate.slack;

import dev.notypie.aggregate.slack.event.SlackEvent;

import java.util.Map;

public interface SlackRequestParser {

    SlackEvent<?> parseRequest(SlackRequestHeaders headers, Map<String, Object> payload);

}
