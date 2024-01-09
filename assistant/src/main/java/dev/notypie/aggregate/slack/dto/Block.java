package dev.notypie.aggregate.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class Block {

    @JsonProperty("type")
    private String type;

    @JsonProperty("block_id")
    private String blockId;

    @JsonProperty("elements")
    private List<Element> elements;

}
