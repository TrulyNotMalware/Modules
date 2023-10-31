package dev.notypie.exceptions;

import dev.notypie.global.error.exceptions.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCodeImpl implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "User Not Found"),
    TOKEN_REISSUE_FAILED(HttpStatus.UNAUTHORIZED, "Reissue failed.");

    private final HttpStatus status;
    private final String message;
}
