package dev.notypie.global.error.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SlackErrorCodeImpl implements ErrorCode{
    NOT_A_VALID_REQUEST(HttpStatus.BAD_REQUEST, " Not a valid request. this try will be recorded.");

    private final HttpStatus status;
    private final String message;
}
