package dev.notypie.infrastructure.impl.command.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class Element {

    @JsonProperty("type")
    @NotNull
    private String type;

    @JsonProperty("text")
    private String text;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("elements")
    private List<Element> elements;

}
