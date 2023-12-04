package dev.notypie.global.error.exceptions;

import dev.notypie.global.error.ArgumentError;
import lombok.Builder;

import java.util.List;


public class SlackDomainException extends RuntimeException{
    private final ErrorCode errorCode;
    private final List<ArgumentError> detail;

    @Builder
    public SlackDomainException(ErrorCode errorCode, List<ArgumentError> argumentErrors){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = argumentErrors;
    }
}
