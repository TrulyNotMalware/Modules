package dev.notypie.aggregate.slack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
class Event {

    @JsonProperty("type")
    private String type;

    //event time stamp
    @JsonProperty("event_ts")
    private String eventTs;


}
