package dev.notypie.aggregate.slack;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.notypie.aggregate.slack.event.AppMentionEvent;
import dev.notypie.aggregate.slack.event.SlackEvent;
import dev.notypie.aggregate.slack.event.UrlVerificationEvent;
import dev.notypie.constants.Constants;
import dev.notypie.global.error.ArgumentError;
import dev.notypie.global.error.exceptions.SlackDomainException;
import dev.notypie.global.error.exceptions.SlackErrorCodeImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Service
@RequiredArgsConstructor
public class DefaultRequestParser implements SlackRequestParser{

    private final ObjectMapper objectMapper;

    @Override
    public SlackEvent<?> parseRequest(SlackRequestHeaders headers, Map<String, Object> payload)  {
        if(!payload.containsKey("type")){
            List<ArgumentError> argumentErrors = new ArrayList<>();
            argumentErrors.add(new ArgumentError("type","null","type cannot be NULL"));
            throw new SlackDomainException(SlackErrorCodeImpl.NOT_A_VALID_REQUEST, argumentErrors);
        }
        String payloadType = payload.get("type").toString();
//        if(payloadType.equals(Constants.URL_VERIFICATION)) return new UrlVerificationEvent(headers);

        return switch (payloadType) {
            case Constants.URL_VERIFICATION -> new UrlVerificationEvent(headers, payload, this.objectMapper);
            case Constants.APP_MENTION -> new AppMentionEvent(headers, payload, this.objectMapper);
            default -> null;
        };
    }
}
