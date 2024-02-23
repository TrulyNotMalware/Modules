package dev.notypie.global.error.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommandErrorCodeImpl implements ErrorCode {
    COMMAND_TYPE_NOT_DETECTED(HttpStatus.BAD_REQUEST, "Type key is not detected"),
    UNKNOWN_COMMAND_TYPE(HttpStatus.INTERNAL_SERVER_ERROR, "Not supported command type.");

    private final HttpStatus status;
    private final String message;
}
