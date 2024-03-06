package dev.notypie.global.error.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCodeImpl implements ErrorCode{
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST,"Invalid Parameter included"),
    INVALID_ARGUMENT_TYPE(HttpStatus.BAD_REQUEST, "Invalid Parameter type included."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Request Method type is not allowed."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND,"Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"Internal Server Error"),
    SQL_CONNECTION_REFUSED(HttpStatus.INTERNAL_SERVER_ERROR, "Could not open connection for transaction."),
    JSON_CONVERT_ERRORS(HttpStatus.INTERNAL_SERVER_ERROR, "Json Convert failed."),
    REGEX_NOT_EXPECTED(HttpStatus.BAD_REQUEST, "the regular expression provided is not expected.");

    private final HttpStatus status;
    private final String message;
}
