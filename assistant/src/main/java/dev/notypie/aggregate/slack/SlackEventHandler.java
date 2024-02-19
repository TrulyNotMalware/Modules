package dev.notypie.aggregate.slack;

import com.slack.api.methods.Methods;
import dev.notypie.aggregate.slack.dto.SlackApiResponse;
import dev.notypie.aggregate.slack.dto.SlackChatEventContents;
import dev.notypie.aggregate.slack.dto.SlackEventContents;
import dev.notypie.global.error.exceptions.SlackDomainException;
import dev.notypie.global.error.exceptions.SlackErrorCodeImpl;
import dev.notypie.requester.RestClientRequester;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static dev.notypie.requester.RestClientRequester.defaultContentType;

@Profile("slack")
@Slf4j
@RequiredArgsConstructor
@Service
public class SlackEventHandler implements EventHandler<SlackEventContents, SlackEventResponse> {

    @Value("${slack.api.token}")
    private String botToken;

    @Value("${slack.api.channel}")
    private String channel;

    private final RestClientRequester requester;
//    private static final String defaultContentType = "application/json; charset=utf-8";
//    private final RestClient restClient = RestClient.builder()
//            .baseUrl(Constants.SLACK_API_ENDPOINT)
//            .defaultHeaders(headers -> {
//                headers.add(HttpHeaders.CONTENT_TYPE, defaultContentType);
//                headers.add(HttpHeaders.AUTHORIZATION, "Bearer "+this.botToken);
//            })
//            .build();
    @Override
    public ResponseEntity<SlackEventResponse> generateEventResponse(SlackEventContents event) {
        if(event.getType().equals(Methods.CHAT_POST_MESSAGE)){
            log.info("Chat Post requests");
            SlackChatEventContents chatEvent = (SlackChatEventContents) event;
            ResponseEntity<SlackApiResponse> response = this.requester.post(Methods.CHAT_POST_MESSAGE, this.botToken, chatEvent.getRequest(), SlackApiResponse.class);
            log.info("response:"+response.getBody());
            if( !Objects.requireNonNull(response.getBody()).isOk() )
                throw new SlackDomainException(SlackErrorCodeImpl.NOT_A_VALID_REQUEST, null);
        }
        return new ResponseEntity<>(SlackEventResponse.builder()
                .contentType(defaultContentType)
                .eventContents(event)
                .build(), HttpStatus.OK);
    }

    //Move to Core Module - Requester.
//    private void post(String uri, ChatPostMessageRequest request){
//        ResponseEntity<Object> response = this.restClient.post()
//                .uri(uri)
//                .header(HttpHeaders.AUTHORIZATION, "Bearer "+this.botToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(request)
//                .retrieve()
//                .toEntity(Object.class);
////        if(!Objects.requireNonNull(response.getBody()).isOk()) throw new SlackDomainException(SlackErrorCodeImpl.REQUEST_FAILED, null);
//        log.info("response : {}", response);
//    }
}