package dev.notypie.infrastructure.impl.command.slack.contexts;

import dev.notypie.aggregate.history.entity.History;
import dev.notypie.infrastructure.impl.command.slack.dto.UrlVerificationDto;
import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.util.List;
import java.util.Map;

@Getter
public class SlackChallengeContext extends SlackContext {

    @Serial
    private static final long serialVersionUID = 4835732166326255476L;

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
