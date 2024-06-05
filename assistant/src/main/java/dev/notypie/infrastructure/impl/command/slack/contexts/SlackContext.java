package dev.notypie.infrastructure.impl.command.slack.contexts;

import com.slack.api.methods.Methods;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import dev.notypie.aggregate.commands.entity.CommandContext;
import dev.notypie.aggregate.history.entity.History;
import dev.notypie.global.error.exceptions.CommandException;
import dev.notypie.global.error.exceptions.SlackDomainException;
import dev.notypie.global.error.exceptions.SlackErrorCodeImpl;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackApiResponse;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackChatEventContents;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackEventContents;
import dev.notypie.infrastructure.impl.command.slack.dto.SlackRequestHeaders;
import dev.notypie.requester.RestClientRequester;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class SlackContext extends CommandContext {
    @Serial
    private static final long serialVersionUID = 5233110641470512868L;
    /**
     * Reference from Slack Bolt sdk context.
     * The basic slack references
     */
    public final boolean tracking;
    public final String requestType;

    public final RestClientRequester restClientRequester;

    public final String channel;
    public final String baseUrl;
    public final String botToken;


    SlackContext(Map<String, List<String>> headers, Map<String, Object> payload, String requestType, boolean tracking){
        super(headers, payload);
        this.tracking = tracking;
        this.requestType = requestType;
        this.channel = null;
        this.baseUrl = null;
        this.botToken = null;
        this.restClientRequester = RestClientRequester.builder().build();
    }

    SlackContext(Map<String, List<String>> headers, Map<String, Object> payload, String requestType, boolean tracking,
                 String channel, String baseUrl, String botToken){
        super(headers, payload);
        this.channel = channel;
        this.baseUrl = baseUrl;
        this.botToken = botToken;
        this.requestType = requestType;
        this.tracking = tracking;
        this.restClientRequester = RestClientRequester.builder().baseUrl(baseUrl).build();
    }

    public ResponseEntity<SlackApiResponse> broadcastBotResponseToChannel(String message){
        SlackChatEventContents chatEvent = SlackChatEventContents.builder()
                .ok(true)
                .type(Methods.CHAT_POST_MESSAGE)
                .request(ChatPostMessageRequest.builder()
                        .channel(this.channel)
                        .text(message)
                        .build())
                .build();
        ResponseEntity<SlackApiResponse> response = this.restClientRequester.post(Methods.CHAT_POST_MESSAGE, this.botToken, chatEvent.getRequest(), SlackApiResponse.class);
        if( !Objects.requireNonNull(response.getBody()).isOk() )
            throw new SlackDomainException(SlackErrorCodeImpl.NOT_A_VALID_REQUEST, null);
        return response;
    }

    public ResponseEntity<SlackApiResponse> broadcastBotResponseToChannel(SlackEventContents event){
        if(event.getType().equals(Methods.CHAT_POST_MESSAGE)){
            SlackChatEventContents chatEvent = (SlackChatEventContents) event;
            ResponseEntity<SlackApiResponse> response = this.restClientRequester.post(Methods.CHAT_POST_MESSAGE, this.botToken, chatEvent.getRequest(), SlackApiResponse.class);
            if( !Objects.requireNonNull(response.getBody()).isOk() )
                throw new SlackDomainException(SlackErrorCodeImpl.NOT_A_VALID_REQUEST, null);
            return response;
        }else throw new RuntimeException("Broadcast type must be chat.postMessage");
    }

//    public ResponseEntity<SlackApiResponse> broadcastBotModalResponseToChannel(){
//        ChatPostMessageRequest.builder().blocks()
//    }

    public SlackRequestHeaders getSlackHeaders() {
        return new SlackRequestHeaders(super.getHeaders());
    }

    public abstract History buildEventHistory();
    public void sendExceptionResponseToClient(CommandException commandException){
        this.broadcastBotResponseToChannel(
                "[Error] "+ commandException.getErrorMessage()
        );
    }
}