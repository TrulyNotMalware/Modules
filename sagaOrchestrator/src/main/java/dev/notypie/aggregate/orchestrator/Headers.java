package dev.notypie.aggregate.orchestrator;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.converter.JsonStringConverter;

import java.util.Date;

public class Headers {

    @JsonProperty("PARTITION_ID") // UUID
    private String partitionId;

    @JsonProperty("DATE")
    private String date;

    @JsonProperty("DESTINATION")
    private String destination;

    @JsonProperty("ID") // UUID
    private String ID;

    private PublishHeaders publishHeaders;

    private ReplyHeaders replyHeaders;

    public static class HeaderConverter extends JsonStringConverter<Headers>{
        public HeaderConverter() {
            super(new ObjectMapper());
        }
    }
}