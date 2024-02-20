package dev.notypie.infrastructure.impl.command.slack;

import java.util.*;

public class SlackRequestHeaders {
    /**
     * Reference from Slack-sdk RequestHeaders.
     */
    private final Map<String, List<String>> underlying = new HashMap<>();

    public Set<String> getNames() {
        return underlying.keySet();
    }

    public SlackRequestHeaders(Map<String, List<String>> headers) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            this.underlying.put(normalizeKey(entry.getKey()), entry.getValue());
        }
    }
    public List<String> getMultipleValues(String name) {
        return this.underlying.get(name.toLowerCase(Locale.ENGLISH));
    }
    private static String normalizeKey(String name) {
        return name != null ? name.toLowerCase(Locale.ENGLISH) : null;
    }
}
