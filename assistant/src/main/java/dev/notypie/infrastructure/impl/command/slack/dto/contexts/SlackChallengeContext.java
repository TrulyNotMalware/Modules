package dev.notypie.infrastructure.impl.command.slack.dto.contexts;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.notypie.aggregate.history.entity.History;
import dev.notypie.infrastructure.impl.command.slack.dto.UrlVerificationDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class SlackChallengeContext extends SlackContext {

    private final UrlVerificationDto urlVerificationDto;

    @Builder
    public SlackChallengeContext(UrlVerificationDto urlVerificationDto, String requestType,
                                 Map<String, List<String>> headers, Map<String, Object> payload) {
        super(headers, payload, requestType, true);
        this.urlVerificationDto = urlVerificationDto;
    }

    @Override
    public History buildEventHistory() {
        return History.builder().build();
    }

    @Override
    public void executeCommand() {

    }

    @Override
    public void validateCommand() {

    }
}
