package dev.notypie.aggregate.slack.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Getter
@SuperBuilder
public abstract class SlackEventContents {
    /**
     * Reference from Slack API Response.
     * Remove setters.
     */
    //SlackApiTextResponse.java
    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    //Event response type.
    private String type;
}