package dev.notypie.aggregate.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class SlackEventRequest {

    @JsonProperty("type")
    @NotNull
    private String type;

    @JsonProperty("token")
    @NotNull
    private String token;

    @JsonProperty("team_id")
    @NotNull
    private String teamId;

    @JsonProperty("api_app_id")
    @NotNull
    private String apiAppId;

    @JsonProperty("event")
    @NotNull
    private Event event;
}


//{
//        "token": "Jhj5dZrVaK7ZwHHjRyZWjbDl",
//        "challenge": "3eZbrw1aBm2rZgRNFdxV2595E9CY3gmdALWMmHkvFXO7tYXAYM8P",
//        "type": "url_verification"
//        }