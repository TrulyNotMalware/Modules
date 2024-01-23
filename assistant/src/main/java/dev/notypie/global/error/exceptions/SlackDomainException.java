package dev.notypie.global.error.exceptions;

import dev.notypie.global.error.ArgumentError;
import lombok.Builder;

import java.io.Serial;
import java.util.List;


public class SlackDomainException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1753611672196255362L;
    private final ErrorCode errorCode;
    private final List<ArgumentError> detail;

    @Builder
    public SlackDomainException(ErrorCode errorCode, List<ArgumentError> argumentErrors){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = argumentErrors;
    }
}
