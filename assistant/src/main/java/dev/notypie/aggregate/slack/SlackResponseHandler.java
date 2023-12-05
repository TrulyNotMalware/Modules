package dev.notypie.aggregate.slack;

import com.slack.api.bolt.response.Response;
import dev.notypie.aggregate.slack.dto.SlackEventContents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Profile("slack")
@Slf4j
@Service
public class SlackResponseHandler implements EventResponseHandler<SlackEventContents, SlackEventResponse>{

    private static final String defaultContentType = "application/json; charset=utf-8";
    @Override
    public ResponseEntity<SlackEventResponse> generateEventResponse(SlackEventContents event) {
        return new ResponseEntity<>(SlackEventResponse.builder()
                .contentType(defaultContentType)
                .eventContents(event)
                .build(), HttpStatus.OK);
    }
}