package dev.notypie.global.error.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SlackErrorCodeImpl implements ErrorCode{
    NOT_A_VALID_REQUEST(HttpStatus.BAD_REQUEST, " Not a valid request. this try will be recorded."),
    EVENT_NOT_SUPPORTED(HttpStatus.INTERNAL_SERVER_ERROR, "This type of event is not supported yet."),
    REQUEST_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Request to slack api server is failed.");

    private final HttpStatus status;
    private final String message;
}
