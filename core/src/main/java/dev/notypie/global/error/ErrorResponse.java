package dev.notypie.global.error;

import dev.notypie.global.error.exceptions.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ErrorResponse {
    private final HttpStatus status;
    private final String message;
    private final List<ArgumentError> detail;


    @Builder
    public ErrorResponse(ErrorCode errorCode, List<ArgumentError> argumentErrors){
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.detail = argumentErrors;
    }
}
