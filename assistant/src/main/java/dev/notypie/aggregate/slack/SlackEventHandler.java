package dev.notypie.aggregate.slack;

import com.slack.api.methods.Methods;
import com.slack.api.methods.SlackApiRequest;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import dev.notypie.aggregate.slack.dto.SlackChatEventContents;
import dev.notypie.aggregate.slack.dto.SlackEventContents;
import dev.notypie.constants.Constants;
import dev.notypie.global.error.exceptions.SlackDomainException;
import dev.notypie.global.error.exceptions.SlackErrorCodeImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Objects;

@Profile("slack")
@Slf4j
@Service
public class SlackEventHandler implements EventHandler<SlackEventContents, SlackEventResponse> {

    @Value("${slack.api.token}")
    private String botToken;

    @Value("${slack.api.channel}")
    private String channel;
    private static final String defaultContentType = "application/json; charset=utf-8";
    private final RestClient restClient = RestClient.builder()
            .baseUrl(Constants.SLACK_API_ENDPOINT)
            .defaultHeaders(headers -> {
                headers.add(HttpHeaders.CONTENT_TYPE, defaultContentType);
                headers.add(HttpHeaders.AUTHORIZATION, "Bearer "+this.botToken);
            })
            .build();
    @Override
    public ResponseEntity<SlackEventResponse> generateEventResponse(SlackEventContents event) {
        if(event.getType().equals(Methods.CHAT_POST_MESSAGE)){
            log.info("Chat Post requests");
            SlackChatEventContents chatEvent = (SlackChatEventContents) event;
            post(Methods.CHAT_POST_MESSAGE, chatEvent.getRequest());
        }
        return new ResponseEntity<>(SlackEventResponse.builder()
                .contentType(defaultContentType)
                .eventContents(event)
                .build(), HttpStatus.OK);
    }

    private void post(String uri, ChatPostMessageRequest request){
        ResponseEntity<Object> response = this.restClient.post()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+this.botToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(Object.class);
//        if(!Objects.requireNonNull(response.getBody()).isOk()) throw new SlackDomainException(SlackErrorCodeImpl.REQUEST_FAILED, null);
        log.info("response : {}", response);
    }
}